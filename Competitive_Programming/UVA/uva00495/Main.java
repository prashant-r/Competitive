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
		memo = new BigInteger[1000000];
		while(scanner.hasNextLine())
		{
			int number = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			out.printf("The Fibonacci number for %d is %d\n" ,number, findFibonacci(number));
		}
	}

	BigInteger memo[];
	public BigInteger findFibonacci(int number)
	{
		if(number == 0)
			return BigInteger.valueOf(0);
		if(number == 1)
			return BigInteger.valueOf(1);
		if(memo[number] != null)
			return memo[number];
		return (memo[number] = findFibonacci(number-1).add(findFibonacci(number-2)));
	}
}