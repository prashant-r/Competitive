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
	int numOffers; 
	Pair offers[];
	int itemRequests[];
	PrintWriter out;
	Pair memo[];
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		int caseNo = 0;
		while(scanner.hasNextLine())
		{
			double d = scanner.nextDouble();
			int x = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			offers = new Pair[x+1];
			numOffers = x;
			offers[0] = new Pair(x,d);
			while(x-- > 0)
			{
				int n = scanner.nextInt();
				double p = scanner.nextDouble();
				if(scanner.hasNextLine())
					scanner.nextLine();
				offers[numOffers - x] = new Pair(n, p);
			}
			Scanner sc = new Scanner(scanner.nextLine());
			itemRequests = new int[100];
			int index = 0;
			while(sc.hasNext())
			{
				itemRequests[index++] = sc.nextInt();
			}
			out.printf("Case %d:\n" , (++caseNo));
			for(int i = 0; i < index; i++)
			{
				Pair answer = minCostPossible(itemRequests[i]);
				out.printf("Buy %d for $%.2f\n" , itemRequests[i], answer.cost);
			}
		}
	}

	class Pair
	{
		int numToBuy;
		double cost;
		public Pair(int numToBuy, double cost)
		{
			this.numToBuy = numToBuy;
			this.cost = cost;
		}
	}

    double [] dp;

	public Pair minCostPossible(int x)
	{

        int MAX_K = 100;
        dp = new double[MAX_K+1];
        for (int k = 1; k <= MAX_K; ++k)
            dp[k] = offers[0].cost * k;
        for (int i = 1; i < numOffers; ++i)
        {
            int n = offers[i].numToBuy;
            double p = offers[i].cost;
            for (int k = 0; k <= MAX_K - n; ++k)
                for (int m = 1; m <= n; ++m)
                    dp[k + m] = Math.min(dp[k + m], dp[k] + p);
        }
        return new Pair(0,dp[x]);   
	}
}