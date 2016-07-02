import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;

// Accepted on UVA :)

class Main {
	public Main() {

	}
	public static void main(String args[]) throws Exception {
		new Main().run(args);
	}
	PrintWriter out;
	HashMap<String, Vertex> vertexHash = new HashMap<String,Vertex>();
	SimpleGraph G;
	public void run(String args[]) throws Exception {
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		List<String> edgeInformation = new ArrayList<String>();
		
		int numTestCases = scanner.nextInt();
		if (scanner.hasNextLine())
			scanner.nextLine();
		while (numTestCases-- > 0) {

			edgeInformation = new ArrayList<String>();
			vertexHash = new HashMap<String,Vertex>();
			G = new SimpleGraph();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.charAt(0) == '*')
				{	
					if(scanner.hasNextLine());
						createVertices(scanner.nextLine());
					break;
				}
				else
					edgeInformation.add(line);
			}
			for(String edgeInfo: edgeInformation)
				createEdge(edgeInfo);
			DFS();
		}
	}
	public void DFS()
	{
		Stack<Vertex> stack = new Stack<Vertex>();
		Vertex v, w, a, b, c;
		Edge e, x, y;
		Iterator<Vertex> i;
		HashSet<Vertex> result = new HashSet<Vertex>();
		for (i = G.vertices(); i.hasNext(); ) {
			w = (Vertex) i.next();
			if(!w.activated)
			{
				result.add(w);
				w.connected =1;
				stack.add(w);
			}
			else
				continue;
			while(!stack.isEmpty())
			{
				Iterator<Edge> j;
				v = stack.pop();
				if(v!=w)
					w.connected+=1;
				v.activated = true;
				for (j = G.incidentEdges(v); j.hasNext();) {
					e = (Edge) j.next();
				if (e.getFirstEndpoint() == v)
					if(!e.getSecondEndpoint().activated)
						stack.add(e.getSecondEndpoint());
				}
			}
		}
		int trees = 0;
		int acorns = 0;
		for(Vertex res : result)
		{
			if(res.connected == 1)
				acorns +=1;
			else if(res.connected >1)
				trees +=1;
		}
		out.printf("There are %d tree(s) and %d acorn(s).\n", trees, acorns);
	}

	public void createVertices(String txt)
	{
		List<String> elephantList = Arrays.asList(txt.split(","));
		for(String s: elephantList)
		{
			vertexHash.put(s,G.insertVertex(null, s));
		}
	}

	public void createEdge(String txt) {
		String re1 = "(\\()";	// Any Single Character 1
		String re2 = "(.)";	// Any Single Character 2
		String re3 = "(,)";	// Any Single Character 3
		String re4 = "(.)";	// Any Single Character 4
		String re5 = "(\\))";	// Any Single Character 5
		Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(txt);
		if (m.find()) {
			String c1 = m.group(1);
			String c2 = m.group(2);
			String c3 = m.group(3);
			String c4 = m.group(4);
			String c5 = m.group(5);
			G.insertEdge(vertexHash.get(c2), vertexHash.get(c4), null, c2 + "->" + c4);
			G.insertEdge(vertexHash.get(c4), vertexHash.get(c2), null, c4 + "->" + c2);
		}
	}

	public static class Edge {
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
			if (!isBack) {
				back = new Edge(w, v, (double) 0.0 , null, true);
				back.setBackEdge(this);
			}
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
		public void setBackEdge(Edge j) {
			back = j;
		}
		/**
		 * returns the back edge for this edge. Note, this could return a forward edge or backward edge
		 * in the residual graph based on the edge being invoked on.
		 * @return Edge
		 */
		public Edge getBackEdge() {
			return back;
		}
		/**
		 * updates the edges flow, accoring to the bottleneck passed in by the FF, SFF algorithms that have identifieed
		 * it on an augmenting path. Increase flow by bottleneck on forward edge, do the reverse on a backward edge.
		 * @param toVertex
		 * @param bottleneck
		 */
		public void updateEdgeFlow(Vertex toVertex, double bottleneck) {
			if (bottleneck >= 0) {
				if (toVertex == v1) // Backward Edge
					flow -= bottleneck;
				else if (toVertex == v2) // Forward Edge
					flow += bottleneck;
				else
					throw new IllegalArgumentException("During flow update: No endpoint " + toVertex.getName() + " exists for edge " + v1.getName() + "->" + v2.getName() );
				if (!(flow >= 0))
					throw new IllegalArgumentException("During flow update: Flow is negative!");
				if (!(flow <= (double)capacity))
					throw new IllegalArgumentException("During flow update: Flow is larger than capacity!");
			}
		}
		/**
		 * Given a vertex v that the edge is pointing to return the residual capacity, be it a forward edge
		 * or a backward edge;
		 * @param v
		 * @return double
		 */
		public double residualCapacityToVertex(Vertex v) {
			if (v == v1) // Backward Edge
				return flow;
			else if (v == v2) // Forward Edge
				return (double)capacity - flow;
			else
				throw new IllegalArgumentException("During Query residual cap : No endpoint " + v.getName() + " exists for edge " + v1.getName() + "->" + v2.getName() );
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
		public int remaining() {
			return (int)((double) capacity - (double ) flow );
		}
	}


	public static class SimpleGraph {

		LinkedList<Vertex> vertexList;
		LinkedList<Edge> edgeList;
		double maxEdgeCapacity;


		// Constructor
		public SimpleGraph() {
			this.vertexList = new LinkedList<Vertex>();
			this.edgeList = new LinkedList<Edge>();
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
			e = new Edge(v, w, data, name, false);
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
				w = e.getSecondEndpoint();
			} else if (e.getSecondEndpoint() == v) {
				w = e.getFirstEndpoint();
			} else
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


		/**
		 * Code to test the correctness of the SimpleGraph methods.
		 */
		public static void main(String[] args) {
			// create graph a----b-----c,
			//                X     Y
			// X and Y are objects stored at edges. .

			// All Objects stored will be strings.

			SimpleGraph G = new SimpleGraph();
			Vertex v, w, a, b, c;
			Edge e, x, y;
			v = G.insertVertex(null, "a");
			a = v;
			w = G.insertVertex(null, "b");
			b = w;
			e = G.insertEdge(v, w, null, "X");
			x = e;
			v = G.insertVertex(null, "c");
			c = v;
			e = G.insertEdge(w, v, null, "Y");
			y = e;

			Iterator<Vertex> i;

			System.out.println("Iterating through vertices...");
			for (i = G.vertices(); i.hasNext(); ) {
				v = (Vertex) i.next();
				System.out.println("found vertex " + v.getName());
			}

			System.out.println("Iterating through adjacency lists...");
			for (i = G.vertices(); i.hasNext(); ) {
				v = (Vertex) i.next();
				System.out.println("Vertex " + v.getName());
				Iterator<Edge> j;

				for (j = G.incidentEdges(v); j.hasNext();) {
					e = (Edge) j.next();
					if (e.getFirstEndpoint() == v)
						System.out.println("  found edge " + e.getName());
				}
			}

			System.out.println("Testing opposite...");
			System.out.println("aXbYc is ");
			System.out.println(a);
			System.out.println(x);
			System.out.println(b);
			System.out.println(y);
			System.out.println(c);

			System.out.println("opposite(a,x) is " + G.opposite(a, x));
			System.out.println("opposite(a,y) is " + G.opposite(a, y));
			System.out.println("opposite(b,x) is " + G.opposite(b, x));
			System.out.println("opposite(b,y) is " + G.opposite(b, y));
			System.out.println("opposite(c,x) is " + G.opposite(c, x));
			System.out.println("opposite(c,y) is " + G.opposite(c, y));

		}
	}

	public static class Vertex {
		/** the edge list for this vertex */
		LinkedList<Edge> incidentEdgeList;
		List<Edge> residualEdges;
		private Object data;              // an object associated with this vertex
		private String name;              // a name associated with this vertex
		private int height;
		public int excess;
		private int currentResidualOutEdgeCounter;
		public boolean activated;
		public int labelCount [];
		public int connected;
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
		public String getName() {
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
		public List<Edge> getOutGoingEdges() {
			List<Edge> edgeList = new ArrayList<Edge>();
			Iterator<Edge> j;
			Edge e;
			for (j = getIncidentEdgeList(); j.hasNext();) {
				e = j.next();
				if (e.getFirstEndpoint() == this)
					edgeList.add(e);
			}
			return edgeList;
		}
	 /**
	 * Find and return incoming edges from this vertex in original graph
	 * @return List<Edge>
	 */
		public List<Edge> getIncomingEdges() {
			List<Edge> edgeList = new ArrayList<Edge>();
			Iterator<Edge> j;
			Edge e;
			for (j = getIncidentEdgeList(); j.hasNext();) {
				e = j.next();
				if (e.getSecondEndpoint() == this)
					edgeList.add(e);
			}
			return edgeList;

		}
		/**
		 *  returns the incidentEdgeList's, (which are incident
		 *  edges on this vertex in original graph) iterator
		 * @return Iterator<Edge>
		 */
		public Iterator<Edge> getIncidentEdgeList() {
			return incidentEdgeList.iterator();
		}
		/**
		 *  returns the currentResidualOutEdgeCounter
		 *  which is the variable that keeps track of the index of which out edge in residual graph
		 *  we are currently trying to check for admissibility in Discharge routine
		 * @return currentResidualOutEdgeCounter
		 */
		public int getCurrentOutEdgeCounter() {
			return currentResidualOutEdgeCounter;
		}
		/**
		 * go to the next neighboring vertex in residual graph, by incrementing the edge index.
		 */
		public void incrementCurrentOutEdgeCounter() {
			++currentResidualOutEdgeCounter;
		}
		/**
		 * reset the residual out edge counter which resets to first edge in residual graph's out edges
		 * from this vertex
		 */
		public void resetCurrentOutEdgeCounter() {
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

		public int computeExcess() {
			int outGoingFlow  = 0;
			int incomingFlow = 0;
			Iterator<Edge> j;
			Edge e;
			for (j = getIncidentEdgeList(); j.hasNext();) {
				e = j.next();
				if (e.getFirstEndpoint() == this)
					outGoingFlow += e.getFlow();
				else
					incomingFlow += e.getFlow();
			}
			return incomingFlow - outGoingFlow;
		}



	}
}