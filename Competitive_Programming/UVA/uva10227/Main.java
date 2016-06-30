import java.io.*;
import java.util.*;
import java.math.*;

// Accepted on UVA :)

class Main
{
	public Main()
	{

	}

	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}
	PrintWriter out;

	Set<Integer> numbers = new HashSet<Integer>();
	boolean matrix[][];
	HashMap<Integer, UnionFindByRankWithPathCompression> hash = new HashMap<Integer, UnionFindByRankWithPathCompression>();
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		
		Set<Integer> indices = new HashSet<Integer>();
		while(scanner.hasNextLine())
		{
			int numTestCases = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			if(scanner.hasNextLine())
				scanner.nextLine();
			indices = new HashSet<Integer>();
			while(numTestCases-- >0)
			{
				if(scanner.hasNextLine())
					scanner.nextLine();
				else
					break;
				matrix = new boolean[100][100];
				while(scanner.hasNextLine())
				{
					String nexLine = scanner.nextLine();
					if(nexLine.isEmpty())
						break;
					Scanner sc= new Scanner(nexLine);
					int s = sc.nextInt();
					int x = sc.nextInt();
					matrix[s][x] = true;
					indices.add(s);
					
				}
				UnionFindByRankWithPathCompression uf  = new UnionFindByRankWithPathCompression(indices.size());
				List<Integer> list = new ArrayList<Integer>(indices);
				for(int i = 0; i < list.size()- 1; ++i) {
         				for(int j = i + 1; j < list.size(); ++j) {
           					if(same_opinion(list.get(i), list.get(j))) {
               					uf.union(i,j);
            				}
         				}
      			}
      			out.println(uf.count());
      			if(scanner.hasNextLine())
      				out.println();
			}
			break;
		}
	}

public boolean same_opinion(int indexA, int indexB)
{
   for(int k = 0; k < 100; ++k)
      if(matrix[indexA][k] != matrix[indexB][k])
      {
         return false;
      }
   return true;
}
	
public static class UnionFindByRankWithPathCompression {
    private int[] parent;  // parent[i] = parent of i
    private int[] size;    // size[i] = number of sites in tree rooted at i
                           // Note: not necessarily correct if i is not a root node
    private int count;     // number of components


    public UnionFindByRankWithPathCompression(int N) {
        count = N;
        parent = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components (between <tt>1</tt> and <tt>N</tt>)
     */
    public int count() {
        return count;
    }
  

    /**
     * Returns the component identifier for the component containing site <tt>p</tt>.
     *
     * @param  p the integer representing one site
     * @return the component identifier for the component containing site <tt>p</tt>
     * @throws IndexOutOfBoundsException unless <tt>0 &le; p &lt; N</tt>
     */
    public int find(int p) {
        validate(p);
        int root = p;
        while (root != parent[root])
            root = parent[root];
        while (p != root) {
            int newp = parent[p];
            parent[p] = root;
            p = newp;
        }
        return root;
    }

   /**
     * Returns true if the the two sites are in the same component.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @return <tt>true</tt> if the two sites <tt>p</tt> and <tt>q</tt> are in the same component;
     *         <tt>false</tt> otherwise
     * @throws IndexOutOfBoundsException unless
     *         both <tt>0 &le; p &lt; N</tt> and <tt>0 &le; q &lt; N</tt>
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // validate that p is a valid index
    private void validate(int p) {
        int N = parent.length;
        if (p < 0 || p >= N) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (N-1));  
        }
    }  

    /**
     * Merges the component containing site <tt>p</tt> with the 
     * the component containing site <tt>q</tt>.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @throws IndexOutOfBoundsException unless
     *         both <tt>0 &le; p &lt; N</tt> and <tt>0 &le; q &lt; N</tt>
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }

    /**
     * Reads in a sequence of pairs of integers (between 0 and N-1) from standard input, 
     * where each integer represents some site;
     * if the sites are in different components, merge the two components
     * and print the pair to standard output.
     */

    static PrintWriter out;
    public static void main(String[] args) throws Exception {
        
        Scanner scanner= new Scanner(new FileInputStream(args[0]));
        out = new PrintWriter(System.out, true);
        int N = scanner.nextInt();
        if(scanner.hasNextLine())
            scanner.nextLine();
        UnionFindByRankWithPathCompression uf = new UnionFindByRankWithPathCompression(N);
        while (scanner.hasNextLine()) {
            int p = scanner.nextInt();
            int q = scanner.nextInt();
            if(scanner.hasNextLine())
                scanner.nextLine();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
            out.println(p + " " + q);
        }
        out.println(uf.count() + " components");
    }

}
	
}

