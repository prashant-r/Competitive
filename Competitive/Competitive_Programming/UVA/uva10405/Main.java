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
	int matrix[][];
	public static int costOfMatch = 1;
	public static int costOfMisMatch = 0;
	public static int costOfInsert =0;
	public static int costOfDelete = 0;
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		int testCase = 1;
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while(scanner.hasNextLine())
		{
			String first = scanner.nextLine();
			String second = scanner.nextLine();
			matrix = new int[first.length()+1][second.length() + 1];
			out.println(LCS(first, second));
		}
	}

	public int LCS(String first, String second)
	{
		for(int a= 1; a<= first.length(); a++)
		{
			for(int b = 1; b<=second.length();b++)
			{

				matrix[a][b] = Math.max(Math.max(matrix[a-1][b] + costOfDelete, matrix[a][b-1]+costOfInsert), matrix[a-1][b-1] + ((first.charAt(a-1)== second.charAt(b-1))?costOfMatch:costOfMisMatch));

			}
		}
		return matrix[first.length()][second.length()];
	}
}