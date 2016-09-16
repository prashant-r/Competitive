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
		public Object getData()
		{
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
		List<Edge> residualEdges;
		private Object data;              
		private String name;              
		public boolean activated;
		public int labelCount [];
		public int connected;
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