import java.io.*;
import java.util.*;

// Accepted on UVA :)

class UVa11235 {}

class Main {
	private int[] freq, tree;
	private int size;

	void run(String args[]) throws Exception {
		BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer scanner = new StringTokenizer(bi.readLine());
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		size = Integer.parseInt(scanner.nextToken());

		while (size != 0) {
			int query = Integer.parseInt(scanner.nextToken());
			int[] array = new int[size];
			int[] start = new int[size];
			int[] temp = new int[210000];
			freq = new int[size];

			scanner = new StringTokenizer(bi.readLine());
			int prev = Integer.MAX_VALUE;
			int cur;
			int count = 1;
			for (int i = 0; i < size; ++i) {
				int input = Integer.parseInt(scanner.nextToken());
				array[i] = input;
				cur = array[i];
				if (cur != prev) {
					count = 1;
				}
				prev = cur;
				freq[i] = count++;
			}
			SegmentTreeWithLazyPropogation st = new SegmentTreeWithLazyPropogation(freq);
			for (int i = 0; i < query; ++i) {
				scanner = new StringTokenizer(bi.readLine());
				int x = Integer.parseInt(scanner.nextToken());
				int y = Integer.parseInt(scanner.nextToken());
				if (array[x-1] == array[y-1]) {
					pw.println(y - x + 1); continue;
				} else {
					Main.SegmentTreeWithLazyPropogation.Pair rmq = st.rmaxQ(x - 1, y - 1);
					int indexOfRmq = rmq.idx;
					int answer = -1;
					if (indexOfRmq - (x - 1) >= rmq.value)
						answer = rmq.value;
					else
						answer  = Math.max((indexOfRmq - (x - 1)) + 1, ((y - 1) - (indexOfRmq + 1) >= 0) ? st.rmaxQ(indexOfRmq + 1, y - 1).value : Integer.MIN_VALUE);
					pw.println(answer);
				}
			}
			scanner = new StringTokenizer(bi.readLine());
			size = Integer.parseInt(scanner.nextToken());
		}
		pw.close();
	}

	public static void main(String[] args) throws Exception {
		Main program = new Main();
		program.run(args);
	}

	static class SegmentTreeWithLazyPropogation {
		private Node[] heap;
		private int[] array;
		private int size;
		public static void main(String args[]) throws Exception {
			run(args);
		}

		public SegmentTreeWithLazyPropogation(int [] array) {
			this.array = Arrays.copyOf(array, array.length);
			// The max size of this array is 2*2log(n) + 1
			size = (int) (2 * Math.pow(2.0, Math.floor((Math.log((double) array.length) / Math.log(2.0)) + 1)));
			heap = new Node[size];
			build(1, 0, array.length);
		}

		public int size() {
			return array.length;
		}

		// Initialize the nodes of the Segment Tree
		private void build(int v, int from , int size) {
			heap[v] = new Node();
			heap[v].from = from;
			heap[v].to = from + size - 1;

			if (size == 1) {
				heap[v].sum = array[from];
				heap[v].max = array[from];
				heap[v].maxIdx = from;
			} else {
				//Build childs
				build(2 * v, from, size / 2);
				build(2 * v + 1, from + size / 2, size - size / 2);

				heap[v].sum = heap[2 * v].sum + heap[2 * v + 1].sum;
				//max = max of the children
				heap[v].max = Math.max(heap[2 * v].max, heap[2 * v + 1].max);
				if ( heap[2 * v].max > heap[2 * v + 1].max) {
					heap[v].maxIdx = heap[2 * v].maxIdx;
				} else {
					heap[v].maxIdx = heap[2 * v + 1].maxIdx;
				}
			}
		}

		public void update(int from, int to, int value) {
			update(1, from, to , value);
		}

		public void update(int v, int from, int to , int value) {
			Node n = heap[v];

			if (contains(from, to, n.from, n.to)) {
				change(n, value);
			}
			if (n.size() == 1) return;

			if (intersects(from, to, n.from, n.to)) {
				propagate(v);
				update(2 * v, from, to, value);
				update(2 * v + 1, from, to, value);
				n.sum = heap[2 * v].sum + heap[2 * v + 1].sum;
				n.max = Math.max(heap[2 * v].max, heap[2 * v + 1].max);
			}
		}

		private void propagate(int v) {
			Node n = heap[v];

			if (n.pendingVal != null) {
				change(heap[2 * v], n.pendingVal);
				change(heap[2 * v + 1], n.pendingVal);
				n.pendingVal = null; //unset the pending propagation value
			}
		}

		public void change(Node n, int value) {
			n.pendingVal = value;
			n.sum = n.size() * value;
			n.max = value;
			array[n.from] = value;
		}
		/*
		*	Range Sum Query
		*/
		public int rsq(int from, int to) {
			return rsq(1, from, to);
		}

		private int rsq(int v, int from, int to) {
			Node n = heap[v];
			if (contains(from, to, n.from, n.to)) {
				return heap[v].sum;
			}

			if (intersects(from, to, n.from, n.to)) {
				propagate(v);
				int leftSum = rsq(2 * v, from, to);
				int rightSum = rsq(2 * v + 1, from, to);

				return leftSum + rightSum;
			}

			return 0;
		}

		private boolean contains(int from1, int to1, int from2, int to2) {
			return from2 >= from1 && to2 <= to1;
		}

		private boolean intersects(int from1, int to1, int from2, int to2) {
			return from1 <= from2 && to1 >= from2   //  (.[..)..] or (.[...]..)
			       || from1 >= from2 && from1 <= to2; // [.(..]..) or [..(..)..
		}

		/*
			Range maximum Query
		*/
		public Pair rmaxQ(int v, int from, int to) {
			Node n = heap[v];
			if (contains(from, to, n.from, n.to )) {

				return new Pair(n.maxIdx, n.max);
			}
			if (intersects(from, to, n.from, n.to)) {

				propagate(v);

				Pair leftmax = rmaxQ(2 * v, from, to);
				Pair rightmax = rmaxQ(2 * v + 1, from, to);


				if (leftmax.value >= rightmax.value) {
					return new Pair(leftmax.idx, leftmax.value);
				} else {
					return new Pair(rightmax.idx, rightmax.value);
				}
			}

			return new Pair(-1, Integer.MIN_VALUE);
		}

		public Pair rmaxQ(int from, int to) {
			return rmaxQ(1, from, to);
		}

		public static class Pair {
			int idx;
			int value;
			public Pair(int idx, int value) {
				this.idx = idx;
				this.value = value;
			}
		}

		static PrintWriter out;
		public static void run(String args[]) throws Exception {
			out = new PrintWriter(System.out, true);
			SegmentTreeWithLazyPropogation st = null;
			Scanner scanner = new Scanner(new FileInputStream(args[0]));
			String cmd = "cmp";
			while (true) {
				String[] line = scanner.nextLine().split(" ");

				if (line[0].equals("exit")) break;

				int arg1 = 0, arg2 = 0, arg3 = 0;

				if (line.length > 1) {
					arg1 = Integer.parseInt(line[1]);
				}
				if (line.length > 2) {
					arg2 = Integer.parseInt(line[2]);
				}
				if (line.length > 3) {
					arg3 = Integer.parseInt(line[3]);
				}

				if ((!line[0].equals("set") && !line[0].equals("init")) && st == null) {
					out.println("Segment Tree not initialized");
					continue;
				}
				int array[];
				if (line[0].equals("set")) {
					array = new int[line.length - 1];
					for (int i = 0; i < line.length - 1; i++) {
						array[i] = Integer.parseInt(line[i + 1]);
					}
					st = new SegmentTreeWithLazyPropogation(array);
				} else if (line[0].equals("init")) {
					array = new int[arg1];
					Arrays.fill(array, arg2);
					st = new SegmentTreeWithLazyPropogation(array);

					for (int i = 0; i < st.size(); i++) {
						out.print(st.rsq(i, i) + " ");
					}
					out.println();
				}

				else if (line[0].equals("up")) {
					st.update(arg1 - 1, arg2 - 1, arg3);
					for (int i = 0; i < st.size(); i++) {
						out.print(st.rsq(i, i) + " ");
					}
					out.println();
				} else if (line[0].equals("rsq")) {
					out.printf("Sum from %d to %d = %d%n", arg1, arg2, st.rsq(arg1 - 1, arg2 - 1));
				} else if (line[0].equals("rmq")) {
					out.printf("max from %d to %d = %d%n", arg1, arg2, st.rmaxQ(arg1 - 1, arg2 - 1));
				} else {
					out.println("Invalid command");
				}

			}

		}
		public static class Node {
			int sum;
			int max;
			int maxIdx;
			Integer pendingVal = null;
			int from;
			int to;
			int size() {
				return to - from + 1;
			}

		}

	}
}