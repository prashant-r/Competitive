import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

// Accepted on UVA :)

class Main
{
	
	public Main()
	{


	}

	List<Node> hotelGroupNodes[];
	List<Integer> hotelsList;
	public void run(String args[]) throws Exception
	{

		Scanner scanner = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out, true);
		while(true)
		{
			int n = scanner.nextInt();
			if(n == 0)
				break;
			int numHotels = scanner.nextInt();
			int tmpNumHotels = numHotels;
			int hotels[] = new int[numHotels];
			Node nodes[] = new Node[n+1];
			hotelGroupNodes = new ArrayList[n+1];
			for (int a = 1; a <= n; a++)
			{
				nodes[a] = new Node();
				nodes[a].id = a;
				nodes[a].isHotel = false;
				nodes[a].hotelGroupIndex =-1;
				hotelGroupNodes[a] = new ArrayList<Node>();
				nodes[a].processed = false;
				nodes[a].visited = false;
			}
			hotelsList = new ArrayList<Integer>();
			while(tmpNumHotels-- > 0)
			{

				hotels[tmpNumHotels] = scanner.nextInt();	
				nodes[hotels[tmpNumHotels]].isHotel = true;		
				hotelsList.add(hotels[tmpNumHotels]);
			}	
			if(!nodes[1].isHotel)
				hotelsList.add(1);
			if(!nodes[n].isHotel)
				hotelsList.add(n);
			nodes[1].isHotel = true;
			nodes[n].isHotel = true;

			int numRoads = scanner.nextInt();
			int tmpNumRoads = numRoads;
			while(tmpNumRoads -- > 0)
			{
				int x = scanner.nextInt();
				int y = scanner.nextInt();
				int distance = scanner.nextInt();
				new Edge(nodes[x], nodes[y], distance);
				new Edge(nodes[y], nodes[x], distance);
			}

			dijkstra_connected_components(nodes, hotelsList, n);
			int result = dfs(nodes, hotels, n);
			out.println(result);
		}
	}


	public int dfs(Node nodes[], int [] hotels, int n)
	{

		int a =1;
		Queue<NodeInfo> queue = new LinkedList<NodeInfo>();
		queue.add(new NodeInfo(nodes[a], 0));
		nodes[a].visited = true;
		int answer = 0;
		boolean found = false;
		while(!queue.isEmpty())
		{
			NodeInfo seek = queue.poll();
			Node node = seek.node;
			int depth = seek.depth;
			if(node.id == n)
			{
				found = true;
				answer = depth;
				break;
			}
			node.visited = true;
			for(Node children: hotelGroupNodes[node.listIndex])
			{
				if(children.visited == false)
				{
						queue.add(new NodeInfo(children, depth+1));
				}
			}
		}
		if(found)
			return answer-1;
		return -1;
	}

	public void dijkstra_connected_components(Node nodes[], List<Integer> hotels, int n)
	{
		PriorityQueue <Node> q = new PriorityQueue <Node> ();
	
		int tmp = 0;
		for(Integer a: hotels)
		{
			tmp++;
			if(nodes[a].processed)
				continue;
			q.add(nodes[a]);
			nodes[a].distance = 0;
			Node runningFor = q.peek();
			runningFor.listIndex = tmp;
			while(!q.isEmpty())
			{
				Node node = q.poll();
	
					if(node.isHotel)
					{
						hotelGroupNodes[tmp].add(node);
					}

					for(Edge e : node.neighbors)
					{

						if(e.distance!= Integer.MAX_VALUE)
						{
							if(e.destination.id != runningFor.id)
							{
							if(node.distance + e.distance <= 600)
								{
									
									if(e.destination.hotelGroupIndex == tmp)
									{
										if(node.distance + e.distance < e.destination.distance)
										{
											e.destination.distance = node.distance + e.distance;
											q.add(e.destination);
											e.destination.hotelGroupIndex = tmp;
										}
									}
									else
									{
										e.destination.distance = node.distance + e.distance;
										q.add(e.destination);
										e.destination.hotelGroupIndex = tmp;
									}
								}
							}
						}
					}
			}
		
		}


	}

	class Edge {
	public Node destination;
	public int distance = Integer.MAX_VALUE;

	public Edge(Node source, Node destination, int distance) {
		this.destination = destination;
		this.distance = distance;
		source.neighbors.add(this);
		}
	}

	class NodeInfo
	{
		public Node node;
		public int depth;

		public NodeInfo(Node node, int depth)
		{

			this.node = node;
			this.depth = depth;
		}
	}

	class Node implements Comparable <Node> {

		public ArrayList<Edge> neighbors = new ArrayList<Edge>();
		int id;
		int hotelGroupIndex;
		int distance;
		boolean isHotel;
		boolean processed;
		boolean visited;
		int listIndex;
		public int compareTo(Node e)
		{
			return distance < e.distance ? -1 : ((distance == e.distance) ? 0 : 1);
		}

		public String toString() {
       			 return " \n Node id: " + id + ", distance: " + distance + " is hotel? " + isHotel + " hotelGroupIndex " + hotelGroupIndex;
   		 }
	}


	public static void main(String args[]) throws Exception
	{

		new Main().run(args);
	}
}