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
	PrintWriter out;
	int targetValue;
	int numbers[];
	int numbersSize;
	String solution;
	int dp[][];
	final int offset = 32000;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		int numTestCases = scanner.nextInt();
		numbers = new int[111];
		scanner.nextLine();
		while(numTestCases-->0)
		{
			numbersSize = 0;
			Scanner sc = new Scanner(scanner.nextLine());
			int numNumbers = sc.nextInt();
			numNumbers = numNumbers + 1;
			while(numNumbers-->0)
			{
				numbers[numbersSize++] = sc.nextInt();
			}
			dp = new int[numbersSize][2*offset + 7];
			for(int a= 0; a <numbersSize; a++)
			{
				for(int b = 0; b < 2*offset + 7; b++)
				{
					dp[a][b] = -1;
				}
			}
			targetValue = numbers[--numbersSize];
			solution = "";
			if(printExpression(1, numbers[0], "" + numbers[0]))
				out.println(solution + "=" + targetValue);
			else
				out.println("NO EXPRESSION");
		}
	}
	public boolean printExpression(int pos, int value, String sol)
	{
		if(Math.abs(value) >= offset)
			return false;
		if(dp[pos][value + offset] != -1)
			return (dp[pos][value + offset] == 1) ? true : false;
		if(pos == numbersSize)
		{
			if(value == targetValue)
			{	
				solution = new String(sol);
				return true;
			}
			else
				return false;
		}

		dp[pos][value + offset] = ( printExpression(pos+1, value*numbers[pos], ((value == 0) ? sol : sol + "*" + numbers[pos])) | printExpression(pos+1, value-numbers[pos],sol + "-" + numbers[pos]) |
		printExpression(pos+1, value+numbers[pos], (value == 0) ? sol + numbers[pos] : sol + "+" + numbers[pos]) |((value%numbers[pos] ==0 ) ?printExpression(pos+1, value/numbers[pos], (value == 0) ? sol : sol + "/" + numbers[pos]): false))? 1 : 0;
		return (dp[pos][value+ offset] == 1) ? true : false;
	}
}