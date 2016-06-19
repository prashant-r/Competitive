import java.io.*;
import java.math.*;
import java.util.*;


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
		Scanner scanner = new Scanner(new FileInputStream(args[0]));
		out = new PrintWriter(System.out,true);
		while(scanner.hasNextLine())
		{
			Scanner sc = new Scanner(scanner.nextLine());
			int n = sc.nextInt();
			int k = sc.nextInt();
			int m = sc.nextInt();
			out.println(getBarCodes(n, k, m));
		}
	}

	public int getBarCodes(int n, int k , int m)
	{
		return 1;
	}
}