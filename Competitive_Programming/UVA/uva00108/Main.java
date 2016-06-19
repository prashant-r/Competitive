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
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(new FileInputStream(args[0]));
		out = new PrintWriter(System.out, true);
		while(scanner.hasNextLine())
		{
			int n = scanner.nextInt();
			scanner.nextLine();
			int count =  0;
			count = n*n; 
			while(count-- > 0)
			{
				while(scanner.hasNext())
				{
					int x = scanner.nextInt();
				}
			}	
		}
	}
}