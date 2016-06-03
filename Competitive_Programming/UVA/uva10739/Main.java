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
	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}

	String query;
	int matrix[][];
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		int numTestCases = scanner.nextInt();
		scanner.nextLine();
		int copyNumTestCases = numTestCases;
		while(copyNumTestCases-- >0)
		{
			query = scanner.nextLine();
			matrix = new int[query.length()][query.length()];
			for (int[] row: matrix)
    			Arrays.fill(row, -1);
			out.println("Case " + (numTestCases-copyNumTestCases)+": " + topDownDPApproach(0, query.length()-1));
		}
	}

	public int topDownDPApproach(int a, int b)
	{

		if(matrix[a][b] != -1)
		{
			return matrix[a][b];
		}
		if(b-a < 1)
		{
			matrix[a][b] = 0;
			return 0;
		}
		
		matrix[a][b] = Math.min(Math.min(Math.min(1+topDownDPApproach(a+1, b),1+topDownDPApproach(a, b-1)), 1+topDownDPApproach(a+1, b-1)), (query.charAt(a) == query.charAt(b))?topDownDPApproach(a+1, b-1):Integer.MAX_VALUE);
		
		return matrix[a][b];	

	}


	public int findMinEditDistanceToPalindrome(int a, int b)
	{
		// reverse query string and find edit distance wrt query string. The answer will be the (edit distance + 1)/2
		return -1;
	}
}