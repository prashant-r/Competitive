import java.io.*;
import java.math.*;
import java.util.*;

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
	int memo[];
	public void run(String args[]) throws Exception
	{
		Scanner scanner= new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		memo = new int[50];
		Arrays.fill(memo, -1);
		while(scanner.hasNextLine())
		{
			int number = scanner.nextInt();
			if(number == 0)
				break;
			if(scanner.hasNextLine())
				scanner.nextLine();
			out.println(getDifferentPatternCount(number));
		}
	}


	public int getDifferentPatternCount(int x)
	{
		if(x == 1)
			return 1;
		if(x == 2)
			return 2;
		if(x == 3)
			return 3;
		if(memo[x] != -1)
			return memo[x];
		return (memo[x] = getDifferentPatternCount(x-1) + getDifferentPatternCount(x-2));
	}
}