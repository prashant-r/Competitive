import java.io.*;
import java.util.*;
import java.math.*;


public class Solution
{
	
	List<Pair> matrix;
	PrintWriter out;
	public Solution()
	{

	}

	public static void main(String args[]) throws Exception
	{
		new Solution().run(args);
	}

	public void run(String args[]) throws Exception
	{

		Scanner scanner = new Scanner(new FileInputStream(args[0]));
		out = new PrintWriter(System.out, true);
		while(scanner.hasNextLine())
		{
			int numTest = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			int copyNumTest = numTest;
			matrix = new ArrayList<Pair>();
			while(copyNumTest-- > 0)
			{
				int index = numTest-copyNumTest -1;
				int key = scanner.nextInt();
				Pair pair = new Pair(key,index);
				matrix.add(pair);
			}
			if(scanner.hasNextLine())
				scanner.nextLine();
			out.println(getMinDistance());
		}
	}

	public class Pair implements Comparable<Pair>
	{
		int key;
		int index;

		public Pair(int key, int index)
		{
			this.key = key;
			this.index = index;
		}

		@Override
		public int compareTo(Pair given)
		{
			return new Integer(this.key).compareTo(new Integer(given.key));
		}

	}

	public int getMinDistance()
	{
		Collections.sort(matrix);
		int min = Integer.MAX_VALUE;
		int lastKey = 0;
		int lastIndex = 0;
		boolean started = false;
		for(Pair s : matrix)
		{
			if(started)
			{
				if(s.key == lastKey)
				{
					int candidate = Math.abs(s.index -  lastIndex);
					if( candidate < min)
					{
						min = candidate;
					}
				}
			}
			lastKey = s.key;
			lastIndex = s.index;
			started = true;
		}
		if( min == Integer.MAX_VALUE)
			return -1;
		return min;
	}
}