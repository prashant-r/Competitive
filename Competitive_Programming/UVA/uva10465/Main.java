import java.io.*;
import java.util.*;
import java.math.*;

class Main
{
	
	public Main()
	{

	}
	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}
	class Pair
	{
		int numberOfBurgers = 0;
		int timeWasted = 0;
		public Pair(int numberOfBurgers, int timeWasted)
		{
			this.numberOfBurgers = numberOfBurgers;
			this.timeWasted = timeWasted;
		}
	}
	Pair dp[];
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while(scanner.hasNextLine())
		{
			int n = scanner.nextInt();
			int m = scanner.nextInt();
			int t = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			dp = new Pair[t+1]; 
			for(int a= 0; a<= t; a++)
			{
				dp[a] = new Pair(-1,-1);
			}
			maxBurgers(n, m , t);
		}
	}

	public void maxBurgers(int n, int m , int t)
	{
		for(int a = 1 ; a <= t ; a++)
		{
			if( a - n >=0)
			{
				if(dp[a-n].numberOfBurgers!=-1)
				{
				int newNumBurgers = dp[a-n].numberOfBurgers+1;
				int timeWasted = dp[a-n].timeWasted;
				if(newNumBurgers >= dp[a].numberOfBurgers)
				{
					if(timeWasted == 0)
					{
						dp[a].numberOfBurgers = newNumBurgers;
						dp[a].timeWasted = timeWasted;
					}
				}
				}
				if(a- n == 0)
				{
					int newNumBurgers = 1;
					int timeWasted = 0;
					if(newNumBurgers >= dp[a].numberOfBurgers)
					{
						if(timeWasted == 0)
						{	
							dp[a].numberOfBurgers = newNumBurgers;
							dp[a].timeWasted = timeWasted;
						}
					}
				}
			}
			if( a - m >= 0)
			{
				if(dp[a-m].numberOfBurgers!=-1)
				{
				int newNumBurgers = dp[a-m].numberOfBurgers+1;
				int timeWasted = dp[a-m].timeWasted;
				if(newNumBurgers >= dp[a].numberOfBurgers)
				{
					if(timeWasted == 0)
					{
						dp[a].numberOfBurgers = newNumBurgers;
						dp[a].timeWasted = timeWasted;
					}
				}
				}

				if(a- m == 0)
				{
					int newNumBurgers = 1;
					int timeWasted = 0;
					if(newNumBurgers >= dp[a].numberOfBurgers)
					{
						if(timeWasted == 0)
						{	
							dp[a].numberOfBurgers = newNumBurgers;
							dp[a].timeWasted = timeWasted;
						}
					}
				}

			}
		}
		int timeWasted = 0;
		while(dp[t].numberOfBurgers == -1 && t >0)
		{
			timeWasted++;
			t--;
		}
		if(timeWasted ==0)
			out.println(dp[t].numberOfBurgers);
		else
			if(dp[t].numberOfBurgers == -1)
				out.println(0 +" " + timeWasted );
			else
				out.println(dp[t].numberOfBurgers +" " + timeWasted);	
		return;
	}
}