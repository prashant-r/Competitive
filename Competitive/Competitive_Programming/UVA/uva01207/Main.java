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
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		int matrix[][];
		int costOfInsert = 1;
		int costOfDelete = 1;
		int costOfMisMatch = 1;
		int costOfMatch = 0;
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while(scanner.hasNextLine())
		{
			String first = scanner.nextLine();
			Scanner parser = new Scanner(first);
			int l1 = parser.nextInt();
			String input = parser.next();
			String second = scanner.nextLine();
			parser = new Scanner(second);
			int l2 = parser.nextInt();
			String match = parser.next();
			matrix = new int[l1+1][l2+1];

			for(int a = 0; a <= l1; a++)
			{
				matrix[a][0]= a*costOfInsert;
			}
			for(int b = 0; b <=l2; b++)
			{
				matrix[0][b] = b* costOfDelete;
			}
			matrix[0][0] = 0;

			for(int a =1; a<=l1; a++)
			{
				for(int b = 1; b <=l2;b++)
				{

					matrix[a][b] = Math.min(Math.min(matrix[a-1][b] + costOfDelete, matrix[a][b-1] + costOfInsert), matrix[a-1][b-1] + ((input.charAt(a-1) == match.charAt(b-1))?costOfMatch:costOfMisMatch));
				}
			}

			out.println(matrix[l1][l2]);
		}
	}
}