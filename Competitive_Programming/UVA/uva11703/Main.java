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
	int matrix[];
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(new FileInputStream(args[0]));
		out = new PrintWriter(System.out, true);
		matrix = new int[1000000 +1];
		Arrays.fill(matrix,-1);
		while(scanner.hasNextLine())
		{
			int n = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			if(n == -1)
				break;
			out.println(computeAnswer(n));
		}

	}

	public int computeAnswer(int x)
	{
		if(x == 0)
			return 1;
		if(matrix[x] != -1)
			return matrix[x];
		matrix[x] = ((int)Math.floor(computeAnswer(x - (int) Math.sqrt(x)))  + (int)Math.floor(computeAnswer((int)Math.log(x))) + (int) Math.floor(computeAnswer(x * (int)Math.pow((Math.sin(x)), 2)))) %1000000;
		return matrix[x];
	}
}