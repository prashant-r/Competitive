import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;


// Accepted on UVA :)
class Main
{
	
	public static void main(String args[]) throws Exception 
	{


		new Main().run(args);

	}

	public void run(String args[]) throws Exception
	{

		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext())
		{
			String testCase = scanner.next();
			if(testCase.equalsIgnoreCase("."))
				break;
			System.out.println(computeMinRep(testCase));
		}

	}

	public int computeMinRep(String testCase)
	{
		int answer = testCase.length();
		for(int a = 1; a<= testCase.length()/2; a++)
		{
			int r = 0;
			boolean checkResult = true;
			boolean result = false;
			if(answer % a == 0)
			{
			while(2*a + r <=testCase.length())
			{
				if(testCase.substring(r, r+a).equals(testCase.substring(r+a, 2*a + r)))
				{
					result = true;
				}
				else
				{
					result = false;
				}
				checkResult = checkResult & result;
				r = r +a;
			}

			if(checkResult)
			{
				answer = a;
				break;
			}
			}	
		}

		return testCase.length()/ answer;

	}


	public Main()
	{

	}
}