import java.io.*;
import java.util.*;
import java.math.*;

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
	int n;
	PrintWriter out;
	int matrix[][];
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		int caseNo = 1;
		n = 8;
		while(scanner.hasNextLine())
		{
			matrix = new int[n+1][n+1];
			int copyn = 1;
			OK = ((int)Math.pow(2,n) -1);
			while(copyn-->0)
			{
				Scanner s =  new Scanner(scanner.nextLine());
				int colIndex = 1;
				while(s.hasNext()){     
    				matrix[0][colIndex] = s.nextInt();   
					colIndex++;
				}
			}
			out.printf("Case %d: %d\n",caseNo++, NQueenSolutions(0,0,0, 1));
		}

	}
	int OK;

	public void printMatrix()
	{
		for(int a = 1; a <= n; a++)
		{
			for(int b = 1; b <= n; b++)
			{
				System.out.print(matrix[a][b] + " ");
			}
			System.out.println();
		}

	}


	public int NQueenSolutions(int rw, int ld, int rd, int level)
	{
		if(rw == OK)
		{
			return 0;
		}
		int pos = OK & (~(rw | ld | rd));
		int maxVal = 1000000;
		while(pos >0)
		{
			int p = pos & -pos;
			pos = pos - p;
			maxVal = Math.min(maxVal,((matrix[0][level] == log(p,2) + 1)? 0 : 1) + NQueenSolutions(rw|p , (ld|p) << 1 , (rd|p) >> 1, level + 1));
		}
		return maxVal;
	}

	static int log(int x, int base)
{
    return (int) (Math.log(x) / Math.log(base));
}
}