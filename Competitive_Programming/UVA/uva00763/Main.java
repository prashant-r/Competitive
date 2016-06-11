import java.io.*;
import java.math.*;
import java.util.*;

// Accepted on UVA :)

class Main
{
	BigInteger fibonacci[];
	BigInteger negOne;
	PrintWriter out;
	public Main()
	{

	}
	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}
	public void run(String args[]) throws Exception
	{
		Scanner scanner= new Scanner(new FileInputStream(args[0]));
		out = new PrintWriter(System.out,true);
		fibonacci = new BigInteger[500];
		negOne = new BigInteger("-1");
		Arrays.fill(fibonacci, negOne);
		populateFibonacciSequence(500);

		while(scanner.hasNextLine())
		{
			String a= scanner.nextLine();
			String b = scanner.nextLine();
		
			BigInteger x = processString(a).add(processString(b));

			BigInteger complete = x;
			boolean started = false;
			char answer [] = null;
			int length = 0;
			while(complete.compareTo(new BigInteger("0")) != 0)
			{

				int d = binarySearch(complete);
				if(!started)
				{
					length= d-1;
					answer= new char[d];
				}
				started = true;
				answer[length-d+1] = '1'; 
				complete = complete.subtract(fibonacci[d]);

			}
			for(int d= 0 ; d < length; d++)
			{
				if(answer[d] == '1')
					out.printf("%s", "1");
				else
					out.printf("%s", "0");
			}
			out.println();
			if(scanner.hasNextLine())
			{
				out.println();
				scanner.nextLine();
			}
		}
	}

	public BigInteger processString(String str)
	{
		BigInteger constructNumber = new BigInteger("0");
		int count = 0;
		for(int a= str.length() -1; a>=0; a--)
		{
			Integer x = Integer.parseInt(str.substring(a, a+1));
			if(x == 1)
				constructNumber = fibonacci[count+2].add(constructNumber);
			count ++;
		}
		return constructNumber;

	}

	public BigInteger populateFibonacciSequence(int n)
	{
		if(n == 0)
			return new BigInteger("0"); 
		if(n == 1)
			return new BigInteger("1");
		if(fibonacci[n].compareTo(negOne) != 0)
			return fibonacci[n];
		return (fibonacci[n] = populateFibonacciSequence(n-1).add(populateFibonacciSequence(n-2) ));
	}

	public int binarySearch(BigInteger num)
	{
		int low = 0 ; 
		int high = 500;
		while(low <high)
		{
			int mid = (low+ high)/2;
			if(fibonacci[mid].compareTo(num) >= 0)
			{
				high = mid;
			}
			else
			{
				low = mid+1;
			}
		}
		if(fibonacci[low].compareTo(num) == 0)
		{
		}
		else
		{
			low = low -1;
		}
		return low;
	}
}