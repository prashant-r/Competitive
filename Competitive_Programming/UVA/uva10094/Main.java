import java.io.*;
import java.util.*;
import java.math.*;

// TLE need to improve this some day

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
	public void run(String args[]) throws Exception
	{
		Scanner scanner= new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while(scanner.hasNextLine())
		{
			int n = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			bitmask = (int) Math.pow(2, n) -1;
			boolean solnFound = false;
			for(List<Integer> list : findAllSuchSolutions(new LinkedHashSet<Integer>(), 0, 0, 0))
			{
				for(Integer listVal : list)
				{
					out.printf("%d ", listVal+1);
				}
				out.println();
				solnFound = true;
				break;
			}
			if(!solnFound)
				out.println("Impossible!");
		}
	}
	public int bitmask;

	public List<List<Integer>> findAllSuchSolutions(Set<Integer> soFarList, int prevRows, int ld, int rd)
	{
		List<List<Integer>> lists = new ArrayList<List<Integer>>();
		if(prevRows == bitmask)
		{
			List<Integer> list = new ArrayList<Integer>(soFarList);
			lists.add(list);
			return lists;
		}
		int post = bitmask & (~(prevRows | ld | rd));
		while(post > 0)
		{
			int p = post & -post;
			post = post - p;
			soFarList.add(binlog(p));	
			lists.addAll(findAllSuchSolutions(soFarList, prevRows | p, (ld |p) <<1, (rd|p)>>1));
			if(lists.size() == 1)
			{
				break;
			}
			soFarList.remove(binlog(p));
		}
		return lists;
	}


	public static int binlog( int bits ) // returns 0 for bits=0
	{
    	int log = 0;
    	if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
    	if( bits >= 256 ) { bits >>>= 8; log += 8; }
   		if( bits >= 16  ) { bits >>>= 4; log += 4; }
    	if( bits >= 4   ) { bits >>>= 2; log += 2; }
    	return log + ( bits >>> 1 );
	}
}