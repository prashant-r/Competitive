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
	int nbars;
	int amount;
	PrintWriter out;
	int bars[];
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while(scanner.hasNextLine())
		{
			int t = scanner.nextInt();
			int copyt = t;
			while(copyt-- > 0)
			{
				int lbar = scanner.nextInt();
				amount = lbar;
				scanner.nextLine();
				nbars = scanner.nextInt();
				scanner.nextLine();
				int copynbars = nbars;
				Scanner sc = new Scanner(scanner.nextLine());
				bars = new int[nbars];
				while(copynbars-- > 0)
				{
					int x = sc.nextInt();
					bars[nbars - copynbars -1] = x;
				}
				out.println(findIfSubSetSumPossible()?"YES":"NO");
			}
			break;
		}
	}	
	
	class Pair
	{
		boolean possible;
		public Pair()
		{
			this.possible = false;
		}
		@Override
		public String toString()
		{
			return possible ? "true" : "false";
		}
	}

	public boolean findIfSubSetSumPossible()
	{
		Pair matrix[][] = new Pair[nbars][amount+1];
		
		for(int a = 0; a< nbars; a++)
		{
			for(int b = 0 ; b <= amount; b++)
			{
				matrix[a][b] = new Pair();
			}
		}
		for(int c = 0; c < nbars; c++)
		{
			if(bars[c] <= amount)
				matrix[c][bars[c]].possible = true;
			matrix[c][0].possible = true;
		}
		for(int a = 0; a< nbars; a++)
		{
			
			for(int b = 0; b <= amount; b++)
			{
				if(a >0)
				{
				if(b-bars[a] > 0)
				{
					if(matrix[a-1][b-bars[a]].possible == true)
					{
						matrix[a][b].possible = true;
					}

				}
				if(matrix[a-1][b].possible)
					matrix[a][b].possible = true;
				}
			}
		}
		return matrix[nbars-1][amount].possible;
	}
}