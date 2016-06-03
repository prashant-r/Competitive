import java.io.*;
import java.lang.*;
import java.text.*;
import java.math.*;
import java.util.*;

// Accepted on UVA :)

	class Main
	{
				int V;
				Vertex vertexArray[];
				public Main()
				{


				}

				public void run(String args[]) throws Exception 
				{

					// Test working preflow push
					

					Scanner scanner = new Scanner(System.in);
					PrintWriter out = new PrintWriter(System.out, true);
					while(true)
					{
						SimpleGraph G = new SimpleGraph();
						int M = scanner.nextInt();
						int W = scanner.nextInt();
						if(M == 0 && W ==0)
							break;
						int mcopy = M;
						int wcopy = W;
						V = 2*M -2;
						vertexArray = new Vertex[V+1];
						Vertex s = G.insertVertex(null, "s");
						vertexArray[1] = s;
						Vertex t = G.insertVertex(null, "t"); 
						vertexArray[V] = t;
						for(int a = 2; a < V ; a++)
						{
							vertexArray[a] = G.insertVertex(null, String.valueOf(a));
						}
						while(mcopy-- > 2)
						{

							int i = scanner.nextInt();
							int c = scanner.nextInt();
							int x = i-1;
							G.insertEdge(vertexArray[2*x],vertexArray[2*x+1],(double) c, null);
						}
						while(wcopy-- > 0)
						{
							int j = scanner.nextInt();
							int k = scanner.nextInt();
							int d = scanner.nextInt();
							int x = j-1;
							int y = k-1;
							if(j == 1 && k == M)
							{
								G.insertEdge(vertexArray[1], vertexArray[V],(double) d, null);
								G.insertEdge(vertexArray[V], vertexArray[1],(double)d, null);
							}
							else if(j == M && k == 1)
							{
								G.insertEdge(vertexArray[V], vertexArray[1],(double) d, null);
								G.insertEdge(vertexArray[1], vertexArray[V], (double)d, null);
							}
							else if(j == 1)
							{
								G.insertEdge(vertexArray[1], vertexArray[2*y], (double) d, null);
								G.insertEdge(vertexArray[2*y+1], vertexArray[1], (double) d, null);
							}	
							else if(k == 1)
							{
								G.insertEdge(vertexArray[2*x + 1], vertexArray[1], (double) d, null);
								G.insertEdge(vertexArray[1], vertexArray[2*x], (double) d, null);
							}
							else if(j == M)
							{
								G.insertEdge(vertexArray[V], vertexArray[2*y], (double) d, null);
								G.insertEdge(vertexArray[2*y+1], vertexArray[V], (double) d, null);
							}
							else if(k == M)
							{
								G.insertEdge(vertexArray[2*x + 1], vertexArray[V], (double) d, null);
								G.insertEdge(vertexArray[V], vertexArray[2*x], (double) d, null);
							}
							else
							{
								G.insertEdge(vertexArray[2*x + 1], vertexArray[2*y], (double) d, null);
								G.insertEdge(vertexArray[2*y+1], vertexArray[2*x], (double) d, null);
							}
						}
						PreFlowPushImproved preflow = new PreFlowPushImproved(G);
						out.println((int) preflow.findMaxFlow());
					}
				}

				public static void main(String args[]) throws Exception
				{
					new Main().run(args);
				}

				




				class PreFlowPushImproved
				{


				/** Constructor for setting up the graph G, the queue, and some other initialization requirements.
				 * We maintain for each edge a back edge that has capacity 0 and flow 0 to begin with, and is parts
				 * of the residual out edges for a given vertex
				 * It is recommended to read through Vertex.java and Edge.java before reading the rest of this code.
				 * 
				 */
				private SimpleGraph G;
				private Hashtable<String, Vertex> vertices;
				private int resultFlow;
				private Vertex sink;
				private Vertex source;
				private int labelCount[];
				private int N;
				private Queue<Vertex> workList;
				private HashMap<Vertex, Boolean> active;


				public PreFlowPushImproved(SimpleGraph G)
				{

					vertices = G.verticesNameList();
					this.G = G;
					resultFlow = 0;
					source = vertices.get("s");
					sink = vertices.get("t");
					active = new HashMap<Vertex, Boolean>();
					workList = new LinkedList<Vertex>();
					/**
					 * set the size of label count to be maximum 2V+1 since we are guaranteed that
					 * the heights wont increase beyond 2V
					 */
					labelCount = new int[2*vertices.size()+1];
					N = vertices.size();
					/**
					 * Set all vertices to inactive at the beginning
					 * label count is the number of vertices that are at a specified height
					 * used for gap relabelling heurstic
					 * 
					 */
					for(Vertex v: vertices.values())
					{
						active.put(v, false);
						v.labelCount = labelCount;
					}
				}
				/**
				 * Add the vertex to the work queue if it hasn't been 
				 * activated in the past and has remaining excess that needs to be pushed out.
				 * 
				 */
				public void addToWorkList(Vertex v)
				{
					if(!active.get(v))
						if(v.excess >0 )
						{
							active.put(v, true);
							workList.add(v);
						}
				}
				/**
				 * Push delta flow on edge specified. Do appropriate operations on the back edge as well.
				 * if the to vertex is active and has positive excess then add it to the back of queue to be discharged FIFOly. 
				 * 
				 */
				public void Push(Edge e)
				{
					Vertex from = e.getFirstEndpoint();
					Vertex to = e.getSecondEndpoint();
					int delta = Math.min(from.excess,e.remaining());  
					e.flow += delta;
					e.getBackEdge().flow -= delta;
					to.excess += delta;    
					from.excess -= delta;
					addToWorkList(to);	
				}
				/**
				 * After relabeling a vertex, if the number of vertices at height k prior to relabeling turns to 0
				 * then we invoke the gap relabeling heuristic that sets the height of such vertices that have height > k and belong
				 * to the source side of the minimum cut to max(h[v], |V| +1); since we are declaring that 
				 * these are on the setA side of the minimum cut and the other vertices are on the setB side of the cut.
				 * With each gap relabel we get closer and closer to defining the minimum cut.
				 * 
				 */
				public void gapRelabel(int GAP)
				{
					for(Vertex v: vertices.values())
						if(v.getHeight() > GAP)
						{
							v.setHeight(Math.max(v.getHeight(), vertices.size() + 1));
							addToWorkList(v);
						}
				}
				/**
				 * relabel this vertex because there are no more admissible residual out edges
				 * that the excess at this vertex could be pushed out on
				 * essentially, increase the height of this vertex by the minimum height + 1 of
				 * the residual out edges that still have remaining capacity to push flow on.
				 * 
				 */
				public void relabel(Vertex v) {
					int minHeight = Integer.MAX_VALUE;
					for (Edge e: v.getResidualOutEdges()) 
						if(e.remaining() >0)
							minHeight = Math.min(minHeight, e.getSecondEndpoint().getHeight() + 1);
					//System.out.println("minheight " + minHeight);
					v.setHeight(minHeight);
					addToWorkList(v);
				}

				/**
				 * 
				 * @see deliverables.FlowAlgorithm#findMaxFlow()
				 * Main method that computes the maximum flow
				 * 
				 */
				public double findMaxFlow()
				{	
					int answer = 0;
					labelCount[0] = N;
					source.setHeight(N);
					active.put(source, true);
					active.put(sink, true);
					for (int i = 0; i < source.getResidualOutEdges().size(); i++) {
						source.excess += (double)source.getResidualOutEdges().get(i).getcapacity();
						Push(source.getResidualOutEdges().get(i));
					}
					Vertex v;
					while (!workList.isEmpty()) {
						active.put((v = workList.poll()), false);
						discharge(v);
					}

					answer = -source.computeExcess();
					return (resultFlow = answer);
				}

			/**
			 * Runs as long as the vertex has excess
			 * We are trying to push out as much flow as possible from the vertex
			 * so that each vertex that is not the source and not the sink can 
			 * have 0 excess.
			 * Pseudocode follows from the CLRS textbook pg.683 under RELABEL_TO_FRONT algorithm
			 * if after the relabelling, we no. vertices at orginialHeight turns to
			 * then invoke the gap relabelling heuristic
			 * 
			 */
				public void discharge(Vertex vertex)
				{
					Edge edge = null;		
					while(vertex.excess >0)
					{
						if(vertex.getCurrentOutEdgeCounter() == vertex.getResidualOutEdges().size())
						{
							int originalHeight = vertex.getHeight();
							relabel(vertex);	
							if(labelCount[originalHeight] == 0)
								gapRelabel(originalHeight);
							vertex.resetCurrentOutEdgeCounter();
						}
						edge = vertex.getResidualOutEdges().get(vertex.getCurrentOutEdgeCounter());
						if(admissible(edge))
								Push(edge);

						else
							vertex.incrementCurrentOutEdgeCounter();
					}
					
					
				}
				/**
				 * Edge is admissible if the for edge= (u,v)
				 * u = v + 1, (i.e it is at a higher level than the destination reservoir by a height of 1)
				 * and the edge is in the residual graph.
				 * @return boolean
				 */
				public boolean admissible(Edge edge)
				{
					return edge.remaining() >0 && edge.getFirstEndpoint().getHeight() == edge.getSecondEndpoint().getHeight() + 1;
				}
				

				/**
				 * Check that apart from the source and the sink, no other node has excess
				 * and that the excess at source is the negation of the excess at the sink
				 * the absolute value of this excess at source is the maximum flow at the end 
				 * of the algorithm
				 * @return boolean
				 */
				public boolean validateExcessforNodes()
				{
					Vertex source = vertices.get("s");
					Vertex sink = vertices.get("t");
					for(Vertex v: vertices.values())
						if(v != sink && v != source)
							if(v.computeExcess() > 0.0 || v.computeExcess() < 0.0)
								return false;
					return true & (-source.computeExcess()) == sink.computeExcess();
				}

				/**
				 * validates that the maxFlow is correct by checking height conditions,
				 * checking no more augmenting paths exist and checking that the source excess = -(sink excess)
				 * along with no other vertices have excess> 0. And finally, checking if the maxflow = mincut.
				 * @return boolean
				 */
				public boolean validateMaxFlow() {
					return validateHeights() & validateAugmentPathDoesntExist() &  validateExcessforNodes() & (findMaxFlow() == findMinCut());
				}

				/**
				 * Check that the preflow satisfies height constraints
				 * @return boolean
				 */
				public boolean validateHeights()
				{
					 for (Vertex src: vertices.values()) {
					      for (Edge e : src.getResidualOutEdges()) {
					        Vertex dest = G.opposite(src, e);
					        if (e.remaining() > 0 && src.getHeight() > dest.getHeight() + 1) 
					          return false;
					      }
					    }
					return true;
				}
				/**
				 * Once max flow is computed check that no augmenting path exists from the
				 * source to the sink, just as in the ford - fulkerson algorithm
				 * @return boolean
				 */
				public boolean validateAugmentPathDoesntExist()
				{
					   	HashMap<Vertex, Boolean> visited = new HashMap<Vertex, Boolean>();
					   	for(Vertex v: vertices.values())
					   		visited.put(v, false);
					   	
					    Queue<Vertex> queue = new ArrayDeque<Vertex>();
					    queue.add(source);
					    visited.put(source, true);
					    while (!queue.isEmpty()) {
					      Vertex vertex = queue.poll();
					      for (Edge e: vertex.getResidualOutEdges()) {
					        Vertex dest = G.opposite(vertex, e);
					        if (!visited.get(dest) && e.remaining() >0) {
					        	visited.put(dest, true);
					        	queue.add(dest);
					        }
					      }
					    }
					    if (visited.get(sink)) 
					      return false;
					    return true;
				}
				
				/**
				 * find the minimumCut after having found the maximum flow
				 * find an integer k, 0< k< |V|-1 that defines the setA as vertices with heights >k 
				 * and set B as vertices in V that are not is setA
				 * find the minimum cut by adding the capacities of outgoing edges in the original graph 
				 * that start in SetA and end in SetB
				 * returns the min cut found after identifying the gap.
				 * @return double
				 */
				public double findMinCut()
				{
					List<Vertex> setA  = new ArrayList<Vertex>();
					double answer = 0;
					int GAP = 0;
					for (int a = 1 ; a < vertices.size() ; a ++)
						if(labelCount[a] == 0)
						{
							GAP = a;
							break;
						}
					for(Vertex v: vertices.values())
						if(v.getHeight() > GAP)
							setA.add(v);
					for(Vertex v: vertices.values())
						if(v.getHeight() > GAP)
							for(Edge edge: v.getOutGoingEdges())
								if(!setA.contains(edge.getSecondEndpoint()))
									answer += (double) edge.getcapacity();

					return answer;
				}
			}


				class Edge 
				{

			    /** the first endpoint of the edge */
			    private Vertex v1;
			    
			    /** the second endpoint of the edge */
			    private Vertex v2;
				private Object capacity;  // an object associated with this edge
			    private Object name;  // a name associated with this edge 
			    public double flow;
			    private Edge back;
			    

			    /**
			     * Constructor that allows capacity and a name to be associated
			     * with the edge. Also, construct a back edge that has capacity 0, and points back edge of back edge
			     * to this edge;
			     * @param v     the first endpoint of this edge
			     * @param w     the second endpoint of this edge
			     * @param capacity  capacity to be associated with this edge
			     * @param isBack identifies this as a forward edge or not
			     * @param name  a name to be associated with this edge
			     */
			    public Edge (Vertex v, Vertex w, Object capacity, Object name, boolean isBack) {
			        this.capacity = capacity;
			        this.name = name;
			        this.v1 = v;
			        this.v2 = w;
			        flow = 0;
			        v.residualEdges.add(this);
			        if(!isBack){
			        	back = new Edge(w,v, (double) 0.0 , null,true);
			        	back.setBackEdge(this);
			        }
			    }
			    public String toString()
			    {

			    	return " v1 " + v1.name  + " v2 " + v2.name;
			    }
			    /**
			     * Return the first endpoint of this edge.
			     * @return  the first endpoint of this edge
			     */
			    public Vertex getFirstEndpoint() {
			        return this.v1;
			    }
			    /**
			     * set back edge for this edge to j
			     * @param j
			     */
			    public void setBackEdge(Edge j)
			    {
			    	back = j;
			    }
			    /**
			     * returns the back edge for this edge. Note, this could return a forward edge or backward edge
			     * in the residual graph based on the edge being invoked on.
			     * @return Edge
			     */
			    public Edge getBackEdge()
			    {
			    	return back;
			    }
			    /**
			     * updates the edges flow, accoring to the bottleneck passed in by the FF, SFF algorithms that have identifieed
			     * it on an augmenting path. Increase flow by bottleneck on forward edge, do the reverse on a backward edge.
			     * @param toVertex
			     * @param bottleneck
			     */
			    public void updateEdgeFlow(Vertex toVertex,double bottleneck)
			    {
			    	if(bottleneck >= 0)
			    	{
			    		if(toVertex == v1)// Backward Edge
			    			flow -= bottleneck;
			    		else if(toVertex == v2) // Forward Edge
			    			flow += bottleneck;
			    		else
			    			throw new IllegalArgumentException("During flow update: No endpoint "+ toVertex.getName() + " exists for edge " + v1.getName() +"->" + v2.getName() );
			    		if(!(flow >= 0))
			    			throw new IllegalArgumentException("During flow update: Flow is negative!");
			    		if(!(flow <= (double)capacity))
			    			throw new IllegalArgumentException("During flow update: Flow is larger than capacity!");
			    	}
			    }
			    /**
			     * Given a vertex v that the edge is pointing to return the residual capacity, be it a forward edge
			     * or a backward edge;
			     * @param v
			     * @return double
			     */
			    public double residualCapacityToVertex(Vertex v)
			    {
			    	if(v == v1)  // Backward Edge 
			    		return flow;
			    	else if(v == v2) // Forward Edge
			    		return (double)capacity - flow;
			    	else
			    		throw new IllegalArgumentException("During Query residual cap : No endpoint "+ v.getName() + " exists for edge " + v1.getName() +"->" + v2.getName() );
			    }
			    
			    /**
			     * Return the second endpoint of this edge.
			     * @return  the second endpoint of this edge
			     */
			    public Vertex getSecondEndpoint() {
			        return this.v2;
			    }

			    /**
			     * Return the capacity associated with this edge.
			     * @return  the capacity of this edge
			     */
			    public Object getcapacity() {
			        return this.capacity;
			    }
			        
			    /**
			     * Set the capacity associated with this edge.
			     * @param capacity  the capacity of this edge
			     */
			    public void setcapacity(Object capacity) {
			        this.capacity = capacity;
			    }
			    
			    /**
			     * Return the name associated with this edge.
			     * @return  the name of this edge
			     */
			    public Object getName() {
			        return this.name;
			    }
			    /**
			     * return the flow on this edge
			     * @return double
			     */
			    public double getFlow() {
					return flow;
				}
			/**
			 * set the flow on this edge to the flow in parameter
			 * @param flow
			 */
				public void setFlow(double flow) {
					this.flow = flow;
				}
				/**
				 * Similar to the residualCapacityToVertex 
				 * returns the residual capacity on an edge in residual graph.
				 * Duplicate function, used sometimes.
				 * @return capacity - flow
				 */
				public int remaining()
				{
					return (int)((double) capacity - (double ) flow );
				}
			}
				

				class SimpleGraph {

			    LinkedList<Vertex> vertexList;
			    LinkedList<Edge> edgeList;
			    Hashtable<String, Vertex> vertexNameList;
			    double maxEdgeCapacity;


				// Constructor
			    public SimpleGraph() {
			        this.vertexList = new LinkedList<Vertex>();
			        this.edgeList = new LinkedList<Edge>();
			        this.vertexNameList = new Hashtable<String, Vertex>(); 
			        maxEdgeCapacity = 0.0d;
			    }
			    
			    /**
			     * Return the vertex list of this graph.
			     * @returns  vertex list of this graph
			     */
			    public Iterator<Vertex> vertices() {
			        return vertexList.iterator();
			    }

			    /**
			    * Return the vertex name hash table of this graph
				* @returns the vertex name list of this graph	
			    **/
				public Hashtable<String, Vertex> verticesNameList(){
					return vertexNameList;
				}
				/**
				 *
			     * Return the edge list of this graph.
			     * @returns  edge list of this graph
			     */
			    public Iterator<Edge> edges() {
			        return edgeList.iterator();
			    }

			    /**
			     * Given a vertex, return an iterator to the edge list of that vertex
			     * @param v  a vertex
			     * @returns  an iterator to the edge list of that vertex
			     */
			    public Iterator<Edge> incidentEdges(Vertex v) {
			        return v.incidentEdgeList.iterator();
			    }

			   
			    /**
			     * Return an arbitrary vertex of this graph
			     * @returns  some vertex of this graph
			     */
			    public Vertex aVertex() {
			        if (vertexList.size() > 0)
			            return (Vertex) vertexList.getFirst();
			        else
			            return null;
			    }

			    /**
			     * Add a vertex to this graph.
			     * @param data  an object to be associated with the new vertex
			     * @param name  a name to be associated with the new vertex
			     * @returns  the new vertex
			     */
			    public Vertex insertVertex(Object data, String name) {
			        Vertex v;
			        v = new Vertex(data, name);
			        vertexList.addLast(v);
			        vertexNameList.put(name, v);
			        return v;
			    }

			    /**
			     * Add an edge to this graph.
			     * @param v  the first endpoint of the edge
			     * @param w  the second endpoint of the edge
			     * @param data  data to be associated with the new edge
			     * @param name  name to be associated with the new edge
			     * @returns  the new edge
			     */
			    public Edge insertEdge(Vertex v, Vertex w, Object data, Object name) {
			        Edge e;
			        e = new Edge(v, w, data, name,false);
			        edgeList.addLast(e);
			        v.incidentEdgeList.addLast(e);
			        w.incidentEdgeList.addLast(e);
			        return e;
			    }
			    
			    
			    /**
			     * Given a vertex and an edge, if the vertex is one of the endpoints
			     * of the edge, return the other endpoint of the edge.  Otherwise,
			     * return null.
			     * @param v  a vertex
			     * @param e  an edge
			     * @returns  the other endpoint of the edge (or null, if v is not an endpoint of e)
			     */
			    public Vertex opposite(Vertex v, Edge e) {
			        Vertex w;
			        if (e.getFirstEndpoint() == v) {
			            w= e.getSecondEndpoint();
			        }
			        else if (e.getSecondEndpoint() == v) {
			            w = e.getFirstEndpoint();
			        }
			        else
			            w = null;
			        
			        return w;
			    }
			    
			    /**
			     * Return the number of vertices in this graph.
			     * @returns  the number of vertices
			     */
			    public int numVertices() {
			        return vertexList.size();
			    }

			    /**
			     * Return the number of edges in this graph.
			     * @returns  the number of edges
			     */
			    public int numEdges() {
			        return edgeList.size();
			    }
			    /*
			     * Returns the maxEdgeCapacity in the provided Graph
			     * Application: Scaling max, flow algorithm
			     */
			    public double getMaxEdgeCapacity() {
					return maxEdgeCapacity;
				}

				public void setMaxEdgeCapacity(double maxEdgeCapacity) {
					this.maxEdgeCapacity = maxEdgeCapacity;
				}
			    }

			    class Vertex {
			    /** the edge list for this vertex */
			    LinkedList<Edge> incidentEdgeList;
				List<Edge> residualEdges;
			    private Object data;              // an object associated with this vertex
			    private String name;              // a name associated with this vertex
			    private int height;
			    public int excess;
			    private int currentResidualOutEdgeCounter;
			    private boolean activated;
			    public int labelCount [];
			    
				/**
			     * Constructor that allows data and a name to be associated
			     * with the vertex.
			     * @param data     an object to be associated with this vertex
			     * @param name     a name to be associated with this vertex
			     */
			    public Vertex(Object data, String name) {
			        this.data = data;
			        this.name = name;
			        this.incidentEdgeList = new LinkedList<Edge>();
			        this.height = 0;
			        this.residualEdges = new ArrayList<Edge>();
			        this.excess = 0;
			        this.activated = false;
			        labelCount = null;
			        this.currentResidualOutEdgeCounter = 0;
			    }
			    
			    /**
			     * Return the name associated with this vertex.
			     * @return  the name of this vertex
			     */
			    public String getName(){
			        return this.name;
			    }
			    
			    /**
			     * Return the data associated with this vertex.
			     * @return  the data of this vertex
			     */
			    public Object getData() {
			        return this.data;
			    }
			    
			    /**
			     * Set the data associated with this vertex.
			     * @param data  the data of this vertex
			     */
			    public void setData(Object data) {
			        this.data = data;
			    }
			    /**
			     * return the height variable;
			     * @return height
			     */
			    public int getHeight() {
					return height;
				}
			    
			    /**
			     * decrease label count at original height
			     * increase label count at new height
			     * @param height
			     */
			    
				public void setHeight(int height) {
					labelCount[this.height] --;
					this.height = height;
					labelCount[height]++;
				}
				
				/**
				 * Find and return out going edges from this vertex in original graph.
				 * @return List<Edge>
				 */
				public List<Edge> getOutGoingEdges()
				{
					List<Edge> edgeList = new ArrayList<Edge>();
					Iterator<Edge> j;
			    	Edge e;
			    	for(j = getIncidentEdgeList(); j.hasNext();)
			    	{
			    		e = j.next();
						if(e.getFirstEndpoint() == this)
							edgeList.add(e);
			    	}
					return edgeList;
				}
				
				/**
				 * Find and return incoming edges from this vertex in original graph
				 * @return List<Edge>
				 */
				public List<Edge> getIncomingEdges()
				{
					List<Edge> edgeList = new ArrayList<Edge>();
					Iterator<Edge> j;
			    	Edge e;
			    	for(j = getIncidentEdgeList(); j.hasNext();)
			    	{
			    		e = j.next();
						if(e.getSecondEndpoint() == this)
							edgeList.add(e);
			    	}
					return edgeList;
					
				}
				/**
				 *  returns the incidentEdgeList's, (which are incident
				 *  edges on this vertex in original graph) iterator
				 * @return Iterator<Edge> 
				 */
				public Iterator<Edge> getIncidentEdgeList()
				{
					return incidentEdgeList.iterator();
				}
				/**
				 *  returns the currentResidualOutEdgeCounter
				 *  which is the variable that keeps track of the index of which out edge in residual graph
				 *  we are currently trying to check for admissibility in Discharge routine
				 * @return currentResidualOutEdgeCounter
				 */
			    public int getCurrentOutEdgeCounter()
			    {
			    	return currentResidualOutEdgeCounter;
			    }
			    /**
			     * go to the next neighboring vertex in residual graph, by incrementing the edge index.
			     */
			    public void incrementCurrentOutEdgeCounter()
			    {
			    	++currentResidualOutEdgeCounter;
			    }
			    /**
			     * reset the residual out edge counter which resets to first edge in residual graph's out edges
			     * from this vertex
			     */
			    public void resetCurrentOutEdgeCounter()
			    {
			    	currentResidualOutEdgeCounter = 0;
			    }
			    
			    public List<Edge> getResidualOutEdges() {
					return residualEdges;
				}
			    
			    /**
			     * Compute the excess at a vertex by summing up flow on edges outgoing from this vertex in original graph
			     * and summing up flow on edges incoming to this vertex in original graph, then returning the difference 
			     * these values. Used in the preflow-push algorithm.
			     * @return excess at this vertex
			     */
			    
			    public int computeExcess()
			    {
			    	int outGoingFlow  =0;
			    	int incomingFlow = 0;
			    	Iterator<Edge> j;
			    	Edge e;
			    	for(j = getIncidentEdgeList(); j.hasNext();)
			    	{
			    		e = j.next();
			    		if(e.getFirstEndpoint() == this)
			    			outGoingFlow += e.getFlow();
			    		else
			    			incomingFlow += e.getFlow();
			    	}
			    	return incomingFlow - outGoingFlow;
			    }
			    
			    
				}

			}