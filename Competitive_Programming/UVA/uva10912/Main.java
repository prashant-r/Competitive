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
	HashMap<Integer, Integer> matrix[][];
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		matrix = new HashMap[10000+1][10000+1];
		int caseNo = 1;
		while(scanner.hasNextLine())
		{
			int L = scanner.nextInt();
			int S = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			if(L == 0 && S == 0)
				break;
			out.printf("Case %d: %d\n", (caseNo++), countNumberOfString(L,S, 27));
		}
	}

	public int countNumberOfString(int L , int S, int prev)
	{

		if( L <0 || S < 0) return 0;
		if( L == 0 && S != 0) return 0;
		if( L == 0 && S == 0 ) return 1;
		if( L == 1 && S > 0 && S <=26) {if( S < prev) return 1;}
		if( L == 1 && ((S == 0 ) || S > 26)) return 0;
		if(matrix[L][S] == null) matrix[L][S] = new HashMap<Integer, Integer>();
		else if(matrix[L][S].containsKey(prev)) return matrix[L][S].get(prev);
		int total =0; 
		for(int s = 1; s < prev; s++)
		{

			int numPoss = countNumberOfString(L-1, S- s, s);
			if(numPoss >0)
			{
				total += numPoss;
			}

		}
		matrix[L][S].put(prev, total);
		return total;

	}
}