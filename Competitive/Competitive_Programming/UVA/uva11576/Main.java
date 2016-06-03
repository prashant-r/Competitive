import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

// Accepted on UVA :)
class Main
{
	
	int answer;
	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}
	public String arr[];
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out, true);
		int numTestCases = scanner.nextInt();
		int copyNumTestCases = numTestCases;
		while(copyNumTestCases-- > 0)
		{
			int numLetters = scanner.nextInt();
			int numWords = scanner.nextInt();
			int copyNumWords = numWords;
			arr = new String[copyNumWords];
			answer = numLetters * numWords;
			while(copyNumWords-- > 0)
			{
				arr[numWords-copyNumWords-1] = scanner.next();
			}

			for(int a =0 ; a< numWords-1; a++)
			{
				matching(arr[a], arr[a+1]);
			}

			System.out.println(answer);
		}

	}

	public void matching(String a, String b)
	{

		for(int x = 0; x < a.length(); x++)
		{
			if(a.substring(x, a.length()).equals(b.substring(0, b.length() - x)))
			{
				answer -= (a.length() -x);
				break;
			}

		}

	}

	public Main()
	{
		
	}
}