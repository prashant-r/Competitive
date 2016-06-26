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
	int matrix[][];
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while(scanner.hasNextLine())
		{
			int numTestCases = scanner.nextInt();
			int copyNumTestCases = numTestCases;
			if(scanner.hasNextLine())
				scanner.nextLine();
			
			while(copyNumTestCases--> 0)
			{
			int n = scanner.nextInt();
			int k = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			matrix = new int[1001][1001];
			for(int a =0 ; a <=1000; a++)
				for(int b  = 0; b <=1000 ; b++)
					matrix[a][b] = -1;

			out.println("Case " + (numTestCases- copyNumTestCases) + ": " + findMinTotalWeight(n,k));
			}
		}
	}

	public int findMinTotalWeight(int n, int k)
	{

		if(n <=1)
			return 0;
		if(matrix[n][k] != -1)
			return matrix[n][k];
		int total = Integer.MAX_VALUE;
		for(int i = 1 ; i <=n; i++ )
		{
			total = Math.min(total, n*(k+i) + findMinTotalWeight(i-1, k) + findMinTotalWeight(n-i, k+i));
		}
		matrix[n][k] = total;
		return total;
	}
}