import java.io.*;
import java.math.*;
import java.util.*;

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
		out = new PrintWriter(System.out,true);
		while(scanner.hasNextLine())
		{
			int numTestCases = scanner.nextInt();
			int copyNumTestCases = numTestCases;
			scanner.nextLine();
			while(copyNumTestCases-- > 0)
			{
				int n = scanner.nextInt();
				int t = scanner.nextInt();
				int p = scanner.nextInt();

				matrix = new int[n+1][t+1];
				for(int a= 0; a<=n ; a++)
					for(int b = 0; b <=t; b++)
					{
						matrix[a][b] = -1;
					}
				if(scanner.hasNextLine())
					scanner.nextLine();
				out.println(F(n,p,t));
			}

		}
	}

	public int F(int n, int t, int p)
	{
		if(n == 0 && p == 0) return 1;
		if(n == 0 && p != 0) return 0;
		if(matrix[n][p]!=-1) return matrix[n][p];
		int total = 0;
		for(int s = t; s<= p ; s++)
		{
			total+= F(n-1, t, p-s);
		}
		matrix[n][p] = total;
		return total;
	}
}