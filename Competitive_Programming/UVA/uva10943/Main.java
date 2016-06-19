import java.io.*;
import java.util.*;
import java.math.*;

// Accepted on UVA :)

class Main
{
	
	long MOD = 1000000;
	public Main()
	{

	}

	PrintWriter out;
	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}
	long memo[][];
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		memo = new long[102][102];
		while(scanner.hasNextLine())
		{
			int n = scanner.nextInt();
			int k = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			if(n == 0 && k ==0 )
				break;
			out.println(maxWaysPossible(n,k));
		}
	}

	long maxWaysPossible(int n, int k) {
   		if(n == 0 || k == 1) return 1;
   		long res = 0;
   		if(memo[n][k] > 0) return memo[n][k];
   		for(int i = 0; i <= n; ++i)
      		res = (1 + maxWaysPossible(n - i, k - 1)) % MOD;   
   		return memo[n][k] = res;
	}

}