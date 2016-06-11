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
		while(scanner.hasNextLine())
		{
			processStringTopDownDP(scanner.nextLine());			
		}

	}

	public void processStringTopDownDP(String a)
	{
		matrix = new int[a.length()][a.length()];
		for (int[] row: matrix)
    		Arrays.fill(row, -1);
    	int insertions = 0;
		insertions = processStringTopDownDP(a, 0, a.length()-1);
		int x = 0;
		int y = a.length()-1;
		query = new String(a);
		printSolution(x, y);
		out.printf("%d %s\n", insertions, ans);
		ans = "";
	}

	String ans = "";

	void printSolution(int i,int j)
	{
       if(i>j)return ;

 	   if(query.charAt(i)==query.charAt(j))
    	{
        	ans = ans + (query.charAt(i));
        	printSolution(i+1,j-1);
        	if(i!=j)ans = ans + query.charAt(j);
    	}
    	else if(matrix[i][j]==matrix[i+1][j] +1)
    	{
        	ans = ans + query.charAt(i);
        	printSolution(i+1,j);
        	ans = ans + query.charAt(i);
    	}
    	else if(matrix[i][j]==matrix[i][j-1]+1)
    	{
        	ans = ans + query.charAt(j);
         	printSolution(i,j-1);
         	ans = ans + query.charAt(j);
    	}
	}


	public int processStringTopDownDP(String query, int a, int b)
	{
		if(b-a < 1)
		{
			matrix[a][b] = 0;
			return matrix[a][b];
		}
		if(matrix[a][b] != -1)
			return matrix[a][b];

		if(query.charAt(a) == query.charAt(b))
		{
			return matrix[a][b] = processStringTopDownDP(query, a+1, b-1);
		}
		else
		{
			return matrix[a][b] = Math.min(1+ processStringTopDownDP(query, a+1, b), 1+processStringTopDownDP(query, a, b-1));
		}

	}


}