import java.io.*;
import java.lang.*;
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
	String input;
	String match;

	public enum Verdict
	{
		Match, MisMatch, Insert, Delete
	}

	public static int costOfMatch = 0;
	public static int costOfMisMatch = 1;
	public static int costOfInsert = 1;
	public static int costOfDelete = 1;
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		out = new PrintWriter(System.out, true);
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextLine())
		{
			input = scanner.nextLine();
			match = scanner.nextLine();
			createTransformList();
			if(scanner.hasNextLine())
			{
				out.println();
			}
		}


	}

	class Pair
	{

		int x, y;
		Verdict verdict;
		public Pair(int x, int y, Verdict verdict)
		{
			this.x = x;
			this.y = y;
			this.verdict = verdict;
		}

		boolean hasConverged()
		{
			return x==0 && y==0;
		}
	}

	class Operation
	{
		char operation;
		char character;
		int number;

		public Operation(char operation, char character, int number)
		{
			this.operation = operation;
			this.character = character;
			this.number = number;
		}

		@Override
		public String toString()
		{
			return operation + " " +character + " " +number;
		}
	}

	public void createTransformList()
	{
		int matrix[][] = new int[input.length()+1][match.length()+1];
		Pair backpointer[][] = new Pair[input.length()+1][match.length()+1];

		for(int a= 0; a<= input.length(); a++)
		{
			matrix[a][0] = a*costOfInsert;
			backpointer[a][0] = new Pair(a-1, 0, Verdict.Delete);
		}
		for(int b = 0; b<= match.length(); b++)
		{
			matrix[0][b] = b*costOfDelete;
			backpointer[0][b] = new Pair(0, b-1, Verdict.Insert);
		}

		for(int a= 1; a<=input.length(); a++)
		{
			for(int b= 1; b<=match.length();b++)
			{

				matrix[a][b] = Math.min(Math.min(matrix[a-1][b] + costOfInsert, matrix[a][b-1] + costOfDelete), matrix[a-1][b-1] + ((input.charAt(a-1) == match.charAt(b-1))?costOfMatch:costOfMisMatch));

				if(matrix[a][b] == matrix[a-1][b] + costOfDelete)
				{
					backpointer[a][b] = new Pair(a-1, b, Verdict.Delete);

				}
				else if(matrix[a][b] == matrix[a][b-1] + costOfInsert)
				{
					backpointer[a][b] = new Pair(a,b-1, Verdict.Insert);

				}
				else if(matrix[a][b] == matrix[a-1][b-1] + costOfMisMatch)
				{
					backpointer[a][b] = new Pair(a-1, b-1, Verdict.MisMatch);
				}
				else if(matrix[a][b] == matrix[a-1][b-1] +costOfMatch)
				{
					backpointer[a][b] = new Pair(a-1, b-1, Verdict.Match);
				}
				else
				{
					continue;
				}

			}
		}

		int lowestCount = matrix[input.length()][match.length()];
		int lcCopy = lowestCount;
		int a= input.length();
		int b= match.length();
		Pair lastTrace = new Pair(a,b,Verdict.Match);
		String answer = "";
		while(!lastTrace.hasConverged())
		{
			lastTrace = backpointer[a][b];
			if(lastTrace.verdict == Verdict.MisMatch)
			{
				if(!answer.isEmpty())
				{
					answer = "\n" + answer;
				}
				answer = String.valueOf(lcCopy--) + " Replace " + String.valueOf(b) + "," + match.charAt(b-1) + answer;
			}
			else if(lastTrace.verdict == Verdict.Insert)
			{
				if(!answer.isEmpty())
				{
					answer = "\n" + answer;
				}
				answer = String.valueOf(lcCopy--) + " Insert " + String.valueOf(b) + "," + match.charAt(b-1) + answer; 
			}
			else if(lastTrace.verdict == Verdict.Match)
			{

			}
			else if (lastTrace.verdict == Verdict.Delete)
			{
				if(!answer.isEmpty())
				{
					answer = "\n" + answer;
				}
				answer = String.valueOf(lcCopy--)  + " Delete " + String.valueOf(b+1) + answer;
			}
			a = lastTrace.x;
			b = lastTrace.y;

		}
		out.println(String.valueOf(lowestCount));
		out.println(answer); 
	}



	public boolean validate(String start, List<Operation> groups, String end)
	{
		String result = new String(start);
		for(Operation ops : groups)
		{
				char operation = ops.operation;
				char character = ops.character;
				int number = ops.number;
				if(operation == 'D')
				{
					result = result.substring(0,number-1) + result.substring(number, result.length());
				}
				else if(operation == 'C')
				{
					StringBuilder myName = new StringBuilder(result);
					myName.setCharAt(number-1, character);
					result = myName.toString();
				}
				else
				{
					result = result.substring(0,number-1) + character + result.substring(number-1, result.length());
				}

		}
		return result.equals(end);
	}

}
