import java.util.*;
import java.io.*;
import java.lang.*;
import java.math.*;

class Main
{
	public Main()
	{

	}

	int coinHeights[] ;
	int tableHeights[];
	public void run(String args[]) throws Exception
	{

		Scanner scanner = new Scanner(new FileInputStream(args[0]));
		PrintWriter out = new PrintWriter(System.out, true);
		while(true)
		{
			int n = scanner.nextInt();
			int t = scanner.nextInt();
			if(n == 0 && t == 0)
				break;
			coinHeights = new int[n+1];
			tableHeights = new int[t+1];
			int copyn = n;
			int copyt = t;
			while(copyn-- > 0)
			{
				coinHeights[n-copyn] = scanner.nextInt();
			}
			while(copyt -- > 0 )
			{
				tableHeights[t-copyt] = scanner.nextInt();
			}
		}
	}

	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}
}