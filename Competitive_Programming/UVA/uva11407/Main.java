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

	int matrix[];
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		matrix = new int[100000];
		Arrays.fill(matrix, -1);
		int numTestCases = scanner.nextInt();
		if(scanner.hasNextLine())
			scanner.nextLine();
		while(numTestCases -- >0)
		{
			int x = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			out.println(computeMin(x));
		}
	}

	public int computeMin(int g)
	{
		if(g == 0)
			return 0;
		if( g== 1)
			return 1;
		if( g == 2)
			return 2;
		if( g == 3)
			return 3;
		if( g == 4)
			return 1;
		if( g == 50)
			return 2;
		if(matrix[g] != -1)
		{
			return matrix[g];
		}
		int answer = Integer.MAX_VALUE;
		for(int a = 1; a*a <=g; a++)
		{
			answer = Math.min(answer, computeMin(g - a*a) + 1);
		}
		matrix[g] = answer;
		return answer;
	}
}