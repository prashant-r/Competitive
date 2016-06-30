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
	int dp[][];
	int cutLength[];
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while(scanner.hasNextLine())
		{
			int lengthOfStick = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			if(lengthOfStick == 0)
				break;
			int numPeices = scanner.nextInt();
			dp = new int[numPeices+2][numPeices+2];
			int copyNumPeices = numPeices;
			if(scanner.hasNextLine())
				scanner.nextLine();
			cutLength = new int[numPeices+2];
			while(copyNumPeices-- > 0)
			{
				cutLength[numPeices-copyNumPeices] = scanner.nextInt(); 
			}
			for(int a= 0; a <numPeices+2; a++)
				for(int b= 0 ; b <numPeices+2; b++)
					dp[a][b] = -1;
			cutLength[0] = 0;
			cutLength[numPeices+1] = lengthOfStick;
			out.println("The minimum cutting is " + findMinCuttingCost(0,numPeices+1) + ".");

		}
	}

	public int findMinCuttingCost(int left, int right)
	{
		if(left + 1 == right)
			return 0;
		if(dp[left][right] != -1)
			return dp[left][right];
		int cost = 1000000;
		for(int a= left+1; a < right ;a++)
		{
			cost = Math.min(cost, (cutLength[right] - cutLength[left])  + findMinCuttingCost(left, a) + findMinCuttingCost(a, right));
		}
		dp[left][right] = cost;
		return cost;
	}
}