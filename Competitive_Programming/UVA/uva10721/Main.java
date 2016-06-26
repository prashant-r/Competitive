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
	long matrix[][][];
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out,true);
		while(scanner.hasNextLine())
		{
			Scanner sc = new Scanner(scanner.nextLine());
			int n = sc.nextInt();
			int k = sc.nextInt();
			int m = sc.nextInt();
			matrix = new long[n+1][k+1][m+1];
			for(int a= 0; a <= n ; a++)
				for(int b = 0; b <= k; b++)
					for(int c = 0; c <=m ; c++)
						matrix[a][b][c] = -1;
 			out.println(getBarCodes(n, k, m));
		}
	}

	public long getBarCodes(int n, int k , int m)
	{

		if(k == 1 && n <=m && n >=1)
			return 1;
		if(k <= 0 || n <=0)
		{
			return 0;
		}
		if(matrix[n][k][m] != -1)
			return matrix[n][k][m];
		long total = 0;
		for(int a = 1; a <=m; a++)
		{
			if(n-a>=0)
				total+=getBarCodes(n-a,k-1, m);
			else
				break;
		}
		matrix[n][k][m] = total;
		return total;
	}
}