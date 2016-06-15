import java.io.*;
import java.math.*;
import java.util.*;

// Accepted on UVA

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
	int matrix[];
	Set<String> permutes;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		matrix = new int[9];
		permutes = findDifferentPermutations("BCG", 0, "");
		while(scanner.hasNextLine())
		{
			Scanner sc = new Scanner(scanner.nextLine());
			int count = 9;
			while(count --> 0)
			{
				int n = sc.nextInt();
				matrix[9-count-1]= n;
			}
			findMinBinPacking();
		}
	}

	public void findMinBinPacking()
	{
		Pair result = new Pair(0,"");
		int total = Integer.MAX_VALUE;
		for(String g : permutes)
		{
			int lowerBound = 0;
			int sum = 0;
			for(int a= 0 ; a< g.length(); a++)
			{
				int binUpperBound = (a+1)*3;
				int index = -1;
				if(g.charAt(a) == 'B')
					index = 0;
				if(g.charAt(a) == 'G')
					index = 1;
				if(g.charAt(a) == 'C')
					index = 2;
				for(int u= lowerBound; u < binUpperBound; u++ )
				{
					if(u != (lowerBound + index))
					{
						sum+= matrix[u];
					}
				}
				lowerBound = binUpperBound; 
			}
			if(sum < total)
			{
				result = new Pair(sum, g);
				total = sum;
			}
		}
		out.println(result.result + " " + result.movements);
	}

	class Pair
	{
		String result;
		int movements;

		public Pair(int movements, String result)
		{
			this.movements = movements;
			this.result = result;
		}
	}

	public Set<String> findDifferentPermutations(String binOrig, int start, String soFar)
	{
		Set<String> add = new TreeSet<String>();
		if( start == binOrig.length())
		{
			add.add(soFar);
			return add;
		}
		for(int a= 0; a < binOrig.length(); a++)
		{
			if(soFar.indexOf(binOrig.charAt(a)) == -1)
				add.addAll(findDifferentPermutations(binOrig, start+1, soFar+ binOrig.charAt(a)));
		}
		return add;
	}
}