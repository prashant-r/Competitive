import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.*;

// Accepted on UVA :)

class Main
{
	public Main()
	{


	}

	public static int n, Operation, First, Second;
	public static void main(String args[]) throws NumberFormatException, IOException {
		Scanner Input = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		n = Input.nextInt();
		friends = new UnionFindByRankWithPathCompression(2*(n+1));	
		while(Input.hasNext()) {
			Operation = Input.nextInt(); First = Input.nextInt(); Second = Input.nextInt();
			if(Operation == 0 && First == 0 && Second == 0)		break;
			applyOperation(Operation,First,Second);
		}
		
	}
	static PrintWriter out;
	static UnionFindByRankWithPathCompression friends;


	public static boolean areFriends(int a, int b)
	{
		return (friends.find(a) == friends.find(b));
		
	}

	public static boolean areEnemies(int a, int b)
	{
		return (friends.find(a+n) == friends.find(b) || friends.find(a) == friends.find(b+n));
		
	}

	public static void applyOperation(int c, int a , int b)
	{
		//out.println(c + " " + a + " " + b);
		if(c == 1) // set friends
		{
			if(areEnemies(a,b))
			{
				out.println("-1");
				return;
			}

			friends.union(a,b);
			friends.union(a+n,b+n);
		}
		else if(c == 2) // set enemies
		{
			if(areFriends(a,b))
			{
				out.println("-1");
				return;
			}	
			friends.union(a+n, b);
			friends.union(b+n, a);
		}
		else if(c == 3) // are friends ?
		{
			out.println(areFriends(a,b) ? "1" :"0" );
		}
		else if(c == 4) // are enemies ?
		{
			out.println(areEnemies(a,b) ? "1" :"0");
		}
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