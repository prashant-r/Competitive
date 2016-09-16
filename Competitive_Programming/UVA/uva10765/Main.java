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
	int m;
	Vertex root;
	public void run(String args[]) throws Exception {
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while (scanner.hasNextLine()) {
			n = scanner.nextInt();
			m = scanner.nextInt();
			G = new SimpleGraph();
			if (scanner.hasNextLine())
				scanner.nextLine();
			if (n == 0 && m == 0)
				break;
			vertexHash = new HashMap<Integer, Vertex>();
			dfs_num = new int[n];
			dfs_low = new int[n];
			dfs_parent = new int[n];
			dfs_number_counter = 0;
			art_vertex = new int[n];
			root_children = 0;
			for (int a = 0 ; a < n; a++)
				vertexHash.put(a, G.insertVertex(a, (new Integer(a)).toString()));
			while (true) {
				int a = scanner.nextInt();
				int b = scanner.nextInt();
				if (scanner.hasNextLine())
					scanner.nextLine();
				if (a == -1 && b == -1)
				{
					break;
				}
				else {
					Vertex vertexA = vertexHash.get(a);
					Vertex vertexB = vertexHash.get(b);
					G.insertEdge(vertexA, vertexB, null , vertexA.getName() + " -> " + vertexB.getName());
					G.insertEdge(vertexB, vertexA, null , vertexB.getName() + " -> " + vertexA.getName());
				}
			}
			findArticulationPoints();
		}
	}

	public void findArticulationPoints() {

		dfs_number_counter = 1;
		for (int a = 0 ; a < n; a++) {
			if (dfs_num[a] == UNVISITED) {
				root_children = 0;
				dfs_root = a;
				dfs_parent[dfs_root] = -1;
				findArticulationPoints(a);
				art_vertex[a] = (root_children > 1) ? root_children-1 : 0;
			}
		}

		List<Pair> list = new ArrayList<Pair>();
		for (int a = 0; a < n; a++) {
			art_vertex[a]++;

			//System.out.println(a + " " + art_vertex[a] + " " + dfs_low[a] + " " + dfs_num[a] + " " + dfs_parent[a]);
			list.add(new Pair(a,art_vertex[a]));
		}
		Comparator<Pair> cmp = new Comparator<Pair>() {
			public int compare(Pair o1, Pair o2) {
				int result = Integer.valueOf(o2.count).compareTo(Integer.valueOf(o1.count));
				if(result == 0)
				{
					return Integer.valueOf(o1.index).compareTo(Integer.valueOf(o2.index));
				}
				else
					return result;
			}
		};
		Collections.sort(list, cmp);
		int counter = 0;
		for(Pair pair: list)
		{	
			if(++counter > m)
				break;
			out.println(pair.index + " " + pair.count);
		}
		System.out.println();
	}

	class Pair {
		int index;
		int count;
		public Pair(int index, int count) {
			this.index = index;
			this.count = count;
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
					art_vertex[given]++;
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