import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.*;

// Accepted on UVA :)

class Main
{
	public enum Verdict
	{
		Match, MisMatch, Insert, Delete
	}

	public Main()
	{


	}
	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}
	PrintWriter out;
	String arr1[];
	String arr2[];
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		arr1 = new String[100];
		arr2 = new String[100];	
		while(scanner.hasNextLine())
		{
			String first = "";
			while(true)
			{
				String rec = scanner.nextLine();
				if(rec.equals("#"))
					break;
				first += rec + " ";
			}

			String second = "";
			while(true)
			{
				String rec = scanner.nextLine();
				if(rec.equals("#"))
					break;
				second += rec + " "; 
			}

			arr1 = first.split("\\s+");
			arr2 = second.split("\\s+");
			LCS();
		}
	}



	public static int costOfMatch = 1;
	public static int costOfMismatch = -10000000;
	public static int costOfInsert = 0;
	public static int costOfDelete = 0;

	class Pair
	{
		int x,y;
		Verdict verdict;
		public Pair(int x, int y, Verdict verdict)
		{
			this.x =x;
			this.y =y;
			this.verdict = verdict;
		}

		public boolean hasConverged()
		{
			return x==0 && y==0;
		}
	}

	public void LCS()
	{

		int l1 = arr1.length;
		int l2 = arr2.length;

		int matrix[][] = new int[l1+1][l2+1];
		Pair backPointer[][] = new Pair[l1+1][l2+1];

		for(int a = 0; a<=l1; a++)
		{
			matrix[a][0] = a*costOfInsert;
			backPointer[a][0] = new Pair(a-1,0, Verdict.Insert);
		}

		for(int b = 0; b<=l2; b++)
		{

			matrix[0][b] = b*costOfDelete;
			backPointer[0][b] = new Pair(0, b-1, Verdict.Delete);
		}
		matrix[0][0] = 0;
		for(int a = 1; a<=l1; a++)
		{

			for(int b = 1; b<=l2; b++)
			{

				matrix[a][b] = Math.max(Math.max(matrix[a-1][b] + costOfDelete,matrix[a][b-1] + costOfInsert), matrix[a-1][b-1] + ((arr1[a-1].equals(arr2[b-1]))?costOfMatch:costOfMismatch));
				if(matrix[a][b] == matrix[a-1][b] + costOfDelete)
				{
					backPointer[a][b] = new Pair(a-1, b, Verdict.Delete);
				}
				else if (matrix[a][b] == matrix[a][b-1] + costOfInsert )
				{
					backPointer[a][b] = new Pair(a,b-1, Verdict.Insert);
				}	
				else if(matrix[a][b] == matrix[a-1][b-1] + costOfMismatch)
				{
					
				}
				else
				{

					backPointer[a][b] = new Pair(a-1,b-1, Verdict.Match);
				}

			}

		}

		int a = l1;
		int b = l2;
		String answer = "";
		Pair lastTrace = new Pair(l1,l2, Verdict.MisMatch);
		while(!lastTrace.hasConverged())
		{
			

			lastTrace = backPointer[a][b];

			if(lastTrace.verdict == Verdict.Match)
			{
					answer = " " + arr1[a-1] + answer;
			}

			a = lastTrace.x;
			b = lastTrace.y;
		}

		out.println(answer.substring(1,answer.length()));
	}
}