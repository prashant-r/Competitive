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
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		memo = new int[1000000];
		Arrays.fill(memo, -1);
		prepopulateMemo();
		while(scanner.hasNextLine())
		{
			int x = scanner.nextInt();
			if(x == 0)
				break;
			if(scanner.hasNextLine())
				scanner.nextLine();
			out.println(getCriticalMass(x));
		}
	}
	public void prepopulateMemo()
	{
		memo[1] = 2; // U/L
		memo[2] = 4; // UU/ LL / LU /UL
		memo[3] = 7;// UUL/ LUU/ LLL/ LUL/ LLU/ .. 

	}

	public int getCriticalMass(int g)
	{
		return(int) (Math.pow(2.0, g)) - getSafeSize(g); 
	}

	int memo[];
	public int getSafeSize(int x)
	{	
		if(memo[x] != -1)
			return memo[x];
		else
			return (memo[x] = (getSafeSize(x-1)) + getSafeSize(x-2) + getSafeSize(x-3)); 	
	}



}