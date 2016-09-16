import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.*;

// Accepted on UVA :)

class Main {

	public final int UNVISITED = 0;

	public Main() {


	}
	public static void main(String args[]) throws Exception {
		new Main().run(args);
	}

	PrintWriter out;
	SimpleGraph G;
	HashMap<Integer, Vertex> vertexHash;
	int dfs_num[];
	int dfs_low[];
	int dfs_parent[];
	int dfs_root;
	int root_children;
	int dfs_number_counter;
	int art_vertex[];
	int n;
	HashMap<String, Integer> cityMap;
	Vertex root;
	public void run(String args[]) throws Exception {
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		
		int caseNo =0 ;
		boolean started = false;
		while (scanner.hasNextLine()) {
			caseNo++;
			cityMap = new LinkedHashMap<String, Integer>();
			n = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			if(n == 0)
				break;
			int copyn = n;
			if(started)
				out.println();
			while(copyn-->0)
			{
				String str = scanner.nextLine();
				cityMap.put(str, copyn);
			}
			G = new SimpleGraph();
			vertexHash = new HashMap<Integer, Vertex>();
			dfs_num = new int[n];
			dfs_low = new int[n];
			dfs_parent = new int[n];
			dfs_number_counter = 0;
			art_vertex = new int[n];
			root_children = 0;
			for (int a = 0 ; a < n; a++)
				vertexHash.put(a, G.insertVertex(a, (new Integer(a)).toString()));
			
			int R = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			while(R-->0)
			{
				String spl[] = scanner.nextLine().split("\\s+");
				int a= cityMap.get(spl[0]);
				int b= cityMap.get(spl[1]);
				Vertex vertexA = vertexHash.get(a);
				Vertex vertexB = vertexHash.get(b);
				G.insertEdge(vertexA, vertexB, null , vertexA.getName() + " -> " + vertexB.getName());
				G.insertEdge(vertexB, vertexA, null , vertexB.getName() + " -> " + vertexA.getName());

			}
			findArticulationPointsMain(caseNo);
			started = true;
		}
	}

	public void findArticulationPointsMain(int caseNo) {

		dfs_number_counter = 1;
		for (int a = 0 ; a < n; a++) {
			if (dfs_num[a] == UNVISITED) {
				root_children = 0;
				dfs_root = a;
				dfs_parent[dfs_root] = -1;
				findArticulationPoints(a);
				art_vertex[a] = (root_children > 1) ? 1 : 0;
			}
		}

		Set<String> set = new TreeSet<String>();
		List<String> keys = new ArrayList<>(cityMap.keySet());
		for (int a = 0; a < n; a++) {

			if(art_vertex[a] == 1)
				set.add(keys.get(n-a-1));
		}
		out.printf("City map #%d: %d camera(s) found\n", caseNo, set.size());
		for(String str: set)
		{
			out.println(str);
		}
	}

	public void findArticulationPoints(int given) {
		dfs_num[given] = dfs_low[given] = dfs_number_counter++;
		Vertex aVertex = vertexHash.get(given);
		Iterator<Edge> j;
		Edge e;
		for (j = aVertex.getIncidentEdgeList(); j.hasNext();) {
			e = j.next();
			Vertex opp = G.opposite(aVertex, e);
			int index = (Integer)opp.getData();
			if (dfs_num[index] == UNVISITED) {
				dfs_parent[index] = given;
				if (given == dfs_root)
					root_children++;
				findArticulationPoints(index);
				if (dfs_low[index] >= dfs_num[given])
					art_vertex[given] = 1;
				dfs_low[given] = Math.min(dfs_low[given], dfs_low[index]);
			} else if (dfs_parent[given] != index) {
				dfs_low[given] = Math.min(dfs_low[given], dfs_num[index]);
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