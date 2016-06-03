import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.*;

//Accepted on UVA :)

class Main
{
	public Main()
	{


	}
	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}

	int arr1[];
	int arr2[];
	int l1;
	int l2;
	int matrix[][];
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		int testCase = 0;
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while(true)
		{
			int a = scanner.nextInt();
			int copya = a;

			int b = scanner.nextInt();
			int copyb = b;
			if(a == 0 && b== 0)
				break;

			scanner.nextLine();
			arr1= new int[copya]; 
			while(copya--> 0)
			{
				arr1[a-copya-1] = scanner.nextInt();
			}
			arr2 = new int[copyb];
			scanner.nextLine();
			while(copyb--> 0)
			{
				arr2[b-copyb-1] = scanner.nextInt();
			}
			l1 = a;
			l2 = b;
			matrix = new int[a+1][b+1];

			out.println("Twin Towers #" + ++testCase);
			LCS();
			out.println();

		}	
	}


	public static int costOfMatch = 1;
	public static int costOfMisMatch = -1000000;
	public static int costOfInsert = 0;
	public static int costOfDelete = 0;
	public void LCS()
	{
		for(int a = 1; a<= l1; a++ )
		{
			for(int b = 1; b<=l2; b++)
			{

				matrix[a][b] = Math.max(Math.max(matrix[a-1][b] + costOfDelete, matrix[a][b-1] + costOfInsert), matrix[a-1][b-1] + ((arr1[a-1] == arr2[b-1])?costOfMatch:costOfMisMatch));

			}
		}
		out.println("Number of Tiles : " + matrix[l1][l2]);
	}
}