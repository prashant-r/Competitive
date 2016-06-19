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

	long dp[][];
	int A[];
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(new FileInputStream(args[0]));
		out = new PrintWriter(System.out, true);
		dp = new long [1005][1005];
		while(scanner.hasNextLine())
		{
			
			A = new int[1005];
			int n = scanner.nextInt();
			int m = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			if(n == 0 && m == 0)
				break;
			for(int i = 1; i <= n; i++)
            	A[i] = scanner.nextInt();
            if(scanner.hasNextLine())
            	scanner.nextLine();
       
        	out.printf("%d\n", getGroupingProblemSolution(n, m));

		}
	}


	public long getGroupingProblemSolution(int n , int m)
	{
		dp[0][0] = 1;
        for(int i = 1; i <= n; i++) {
            dp[i][0] = 1;
            for(int j = 1; j <= i; j++) {
                dp[i][j] = dp[i-1][j] + dp[i-1][j-1]*A[i];
                if(dp[i][j] >= m)
                    dp[i][j] %= m;
            }
        }
        long ret = 0;
        for(int i = 1; i <= n; i++)
            ret = Math.max(dp[n][i], ret);
        return ret;
	}
}