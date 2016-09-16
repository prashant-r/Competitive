import java.io.*;
import java.util.*;
import java.math.*;

// Accepted on UVA :)

class Main
{
	private final int UNVISITED = -1;
	public Main()
	{

	}
	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}
	SimpleGraph G;
	HashMap<Integer, Vertex> vertexHash;
	int dfs_num[];
	int dfs_low[];
	int dfs_parent[];
	int dfs_root;
	int root_children;
	int dfs_number_counter;
	boolean visited[];
	int n;
	Stack<Integer> stack;
	PrintWriter out;
	HashMap<String, Integer> nameMap;
	int caseNo;
	List<String> keys;
	public void run(String args[]) throws Exception
	{
		caseNo = 0;
		boolean started = false;
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out,true);
		while(scanner.hasNextLine())
		{
			caseNo++;
			n = scanner.nextInt();
			int m = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			if(n == 0 && m == 0)
				break;
			int copyn = n;
			G = new SimpleGraph();
			nameMap = new LinkedHashMap<String,Integer>();
			vertexHash = new HashMap<Integer, Vertex>();
			dfs_num = new int[n];
			for(int a = 0; a< n; a++)
				dfs_num[a] = -1;
			dfs_low = new int[n];
			dfs_parent = new int[n];
			dfs_number_counter = 1;
			visited = new boolean[n];
			root_children = 0;
			if(started)
				out.println();
			for(int a =0 ; a< copyn; a++)
				vertexHash.put(a, G.insertVertex(a, (new Integer(a)).toString()));
			int copym = m;
			int counter = 0;
			while(copym-->0)
			{
				String spl[] = scanner.nextLine().split("\\s+");
				int a = -1;
				int b = -1;
				if(!nameMap.containsKey(spl[0]))
					nameMap.put(spl[0],counter++);
				if(!nameMap.containsKey(spl[1]))
					nameMap.put(spl[1],counter++);
				a = nameMap.get(spl[0]);
				b = nameMap.get(spl[1]);
				Vertex vertexA = vertexHash.get(a);
				Vertex vertexB = vertexHash.get(b);
				G.insertEdge(vertexA, vertexB, null , vertexA.getName() + " -> " + vertexB.getName());
			}
			keys = new ArrayList<String>(nameMap.keySet());
			findSCC(caseNo);
			started = true;
		}
	}


	public void findSCC(int caseNo) {
		stack = new Stack<Integer>();
		System.out.println("Calling circles for data set " + caseNo + ":");
		dfs_number_counter = 1;
		for (int a = 0 ; a < n; a++) {
			if (dfs_num[a] == UNVISITED) {
				tarjan(a);
			}
		}
	}

	public void tarjan(int given) {
		dfs_num[given] = dfs_low[given] = dfs_number_counter++;
		stack.push(given);
		visited[given] = true;
		Vertex aVertex = vertexHash.get(given);
		Iterator<Edge> j;
		for (Edge e: aVertex.getOutGoingEdges()) {
			Vertex opp = G.opposite(aVertex, e);
			int index = (Integer)opp.getData();
			if (dfs_num[index] == UNVISITED) {
				tarjan(index);	
			} 
			if (visited[given]) {
				dfs_low[given] = Math.min(dfs_low[given], dfs_low[index]);
			}
		}
		int x = given;
		if(dfs_low[x] == dfs_num[x])
		{
			x = -1;
			while(true)
			{
				x = stack.pop();
				visited[x] = false;
				if(x == given)
				{	
					System.out.println(keys.get(x));
					break;
				}
				else
					System.out.print(keys.get(x) + ",");
			}
		}
	}

	public static class Edge {
		private Vertex v1;
		private Vertex v2;
		private Object name;
		private Object data;
		public Edge (Vertex v, Vertex w, Object data, Object name) {
			this.name = name;
			this.data = data;
			this.v1 = v;
			this.v2 = w;
		}
		public Vertex getFirstEndpoint() {
			return this.v1;
		}

		public Vertex getSecondEndpoint() {
			return this.v2;
		}
		public Object getName() {
			return this.name;
		}
		public Object getData() {
			return this.data;
		}
	}


	public static class SimpleGraph {

		LinkedList<Vertex> vertexList;
		LinkedList<Edge> edgeList;
		public SimpleGraph() {
			this.vertexList = new LinkedList<Vertex>();
			this.edgeList = new LinkedList<Edge>();
		}

		public Iterator<Vertex> vertices() {
			return vertexList.iterator();
		}
		public Iterator<Edge> edges() {
			return edgeList.iterator();
		}

		public Iterator<Edge> incidentEdges(Vertex v) {
			return v.incidentEdgeList.iterator();
		}
		public Vertex aVertex() {
			if (vertexList.size() > 0)
				return (Vertex) vertexList.getFirst();
			else
				return null;
		}
		public Vertex insertVertex(Object data, String name) {
			Vertex v;
			v = new Vertex(data, name);
			vertexList.addLast(v);
			return v;
		}
		public Edge insertEdge(Vertex v, Vertex w, Object data, Object name) {
			Edge e;
			e = new Edge(v, w, data, name);
			edgeList.addLast(e);
			v.incidentEdgeList.addLast(e);
			w.incidentEdgeList.addLast(e);
			return e;
		}
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
		public int numVertices() {
			return vertexList.size();
		}
		public int numEdges() {
			return edgeList.size();
		}
	}

	public static class Vertex {
		LinkedList<Edge> incidentEdgeList;
		private Object data;
		private String name;
		public Vertex(Object data, String name) {
			this.data = data;
			this.name = name;
			this.incidentEdgeList = new LinkedList<Edge>();
		}
		public String getName() {
			return this.name;
		}
		public Object getData() {
			return this.data;
		}

		public void setData(Object data) {
			this.data = data;
		}
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
		public Iterator<Edge> getIncidentEdgeList() {
			return incidentEdgeList.iterator();
		}
	}

}