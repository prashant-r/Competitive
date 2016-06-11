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
	long matrix[][];
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		int numTestCases = scanner.nextInt();
		scanner.nextLine();
		while(numTestCases -- > 0)
		{
			query = scanner.nextLine();
			query = query.trim();
			out.println(numbPalindromes(query));
		}
	}

	public long numbPalindromes(String str)
	{
		matrix = new long[str.length()][str.length()];

		for (long[] row: matrix)
    		Arrays.fill(row, 1);

		for(int a =0; a< str.length()-1 ; a++)
		{
			if(str.charAt(a) == str.charAt(a+1))
			{
				matrix[a][a+1] = 3;
			}
			else
			{
				matrix[a][a+1] = 2;
			}
		}

		for(int a =2; a<str.length(); a++)
		{
			for(int i = 0; a+i <str.length(); i++)
			{
				int j = i+a;

				if(str.charAt(i) == str.charAt(j))
				{
					matrix[i][j] = 1 + (matrix[i+1][j] + matrix[i][j-1]);
				}
				else
				{
					matrix[i][j] = matrix[i+1][j] + matrix[i][j-1] - matrix[i+1][j-1]; 
				}
			}

		}

		return matrix[0][str.length()-1];

	}
}
