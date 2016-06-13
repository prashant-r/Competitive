import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.*;


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
		out = new PrintWriter(System.out, true);
		while(scanner.hasNextLine())
		{
			int a= scanner.nextInt();
			findSolutions(a);
		}
	}

	public void findSolutions(int a)
	{
		for(int s =12345; s < 98765; s++)
		{
			if( s / )	
		}
	}
}