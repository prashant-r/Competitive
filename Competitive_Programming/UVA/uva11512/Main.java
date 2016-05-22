import java.util.*;
import java.io.*;
import java.math.*;
import java.lang.*;

class Main
{
	
	public Main()
	{

	}
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(new FileInputStream(args[0]));
		PrintWriter out = new PrintWriter(System.out, true);
		int numTest = scanner.nextInt();
		int copyNumTest = numTest;
		String [] arr = new String[copyNumTest + 1];
		while(copyNumTest-- >  0)
		{
			String next = scanner.next();
			arr[numTest-copyNumTest] = next; 
		}

	}

	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}

}