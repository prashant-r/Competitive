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
	int matrix[];
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		int caseNo = 1;
		while(scanner.hasNextLine())
		{
			n = scanner.nextInt();
			if(n == 0)
				break;
			scanner.nextLine();
			matrix = new int[n+1];
			int copyn = n;
			OK = ((int)Math.pow(2,n) -1);
			while(copyn-->0)
			{
				String s =  scanner.nextLine();
				int colIndex = 1;
				int bitmask = 0;
				for (int i = 0; i < s.length(); i++){
    				char c = s.charAt(i);        
					if(c == '.')
					{
					}
					else 
					{
						matrix[colIndex]= (matrix[colIndex]) | ((int) Math.pow(2, n-copyn-1));
					}
					colIndex++;
				}
			}
			NQueenSolutions(0,0,0, 1);
			out.printf("Case %d: %d\n",caseNo++,answer);
			answer =0;
		}

	}
	int OK;
	int answer = 0;


	public void NQueenSolutions(int rw, int ld, int rd, int level)
	{
		if(rw == OK)
		{
			answer++;
			return;
		}
		int pos = OK & (~(rw | ld | rd));
		while(pos >0)
		{
			int p = pos & -pos;
			pos = pos - p;
			
			if((~matrix[level] & p) != 0)
				NQueenSolutions(rw|p , (ld|p) << 1 , (rd|p) >> 1, level + 1);
		}
		
	}
}