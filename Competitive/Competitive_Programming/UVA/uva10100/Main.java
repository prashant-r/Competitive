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
	int matrix[][];
	public static int costOfMatch = 1;
	public static int costOfMisMatch = 0;
	public static int costOfInsert = 0;
	public static int costOfDelete = 0;
	String arr1[];
	String arr2[];
	public void run(String args[]) throws Exception
	{
		int testCase = 1;
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while(scanner.hasNextLine())
		{
			String first = scanner.nextLine();
			String second = scanner.nextLine();

			if(first.isEmpty() || second.isEmpty())
			{
				out.format("%2d%s\n", testCase++ ,". Blank!" );  
				continue;
			}
			arr1 = first.split("[\\p{Punct}\\s]+");	
			arr2 = second.split("[\\p{Punct}\\s]+");

			matrix = new int[arr1.length+1][arr2.length+1];

			for(int a = 1; a <= arr1.length; a++)
			{
				for(int b = 1; b <= arr2.length; b++)
				{

						matrix[a][b] = Math.max(Math.max(matrix[a-1][b] + costOfDelete, matrix[a][b-1] + costOfInsert), matrix[a-1][b-1] + ((arr1[a-1].equals(arr2[b-1]))?costOfMatch:costOfMisMatch));
				}
			}
			out.format("%2d%s\n", testCase++ , ". Length of longest match: " + matrix[arr1.length][arr2.length]);  

		}
	}
}