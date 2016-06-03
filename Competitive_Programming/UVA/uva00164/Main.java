import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.*;

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

	public static int costOfInsert = 1;
	public static int costOfDelete = 1;
	public static int costOfMatch = 0;
	public static int costOfMisMatch = 1;
	public static int insertId = 0;
	public static int deleteId = 1;
	public static int matchId = 2;
	public static int spaceId = 4;
	public static int mismatchId = 3;
	public static int nullId = 5;
	
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while(true)
		{

			String strings = scanner.nextLine();
			
			if(strings.charAt(0) == '#')
				break;
			strings = strings.trim();
			String [] aString = strings.split("\\s+");

			String start = aString[0];
			String end = aString[1];
			EditDistance(start, end);
		}
	}

	class Pair
	{

		int x, y, verdict;

		Pair(int x, int y, int verdict)
		{
			this.x = x;
			this.y = y;
			this.verdict = verdict;
		}

		boolean hasConverged()
		{
			return x== 0 && y == 0;
		}
	}

	public void EditDistance(String start, String end)
	{
		//System.out.println(" Start " +  start);
		//System.out.println(" End " + end);

		int [][] matrix = new int[start.length()+1][end.length()+1];

		Pair backPointer[][] = new Pair[start.length()+1][end.length()+1];

		for(int a= 0; a <=start.length(); a++)
		{
			matrix[a][0] = a*1;
			backPointer[a][0] = new Pair(a-1, 0, deleteId);
		}

		for(int b = 0; b <=end.length(); b++)
		{

			matrix[0][b] = b*1;
			backPointer[0][b] = new Pair(0,b-1, insertId);
		}

		matrix[0][0] = 0;

		for(int a = 0; a<= start.length() ; a++)
		{
			for(int b = 0; b <= end.length(); b++)
			{
				if(a > 0 && b > 0)
				{
				matrix[a][b] = Math.min(Math.min(matrix[a][b-1] + costOfInsert , matrix[a-1][b] + costOfDelete ), matrix[a-1][b-1] + ((start.charAt(a-1) == end.charAt(b-1)) ? costOfMatch : costOfMisMatch));	

				if(matrix[a][b] == matrix[a][b-1] + costOfInsert)
				{
					backPointer[a][b] = new Pair(a,b-1, insertId);
				}
				else if(matrix[a][b] == matrix[a-1][b] + costOfDelete)
				{
					backPointer[a][b] = new Pair(a-1, b, deleteId);
				}
				else if(matrix[a][b] == matrix[a-1][b-1] + costOfMisMatch)
				{
					backPointer[a][b] = new Pair(a-1,b-1, mismatchId);
				}
				else
				{
					backPointer[a][b] = new Pair(a-1, b-1, matchId);
				}
				}
			//	System.out.print(matrix[a][b] + " ");
			}
			//System.out.println();
		}

		Pair backTrace = backPointer[start.length()][end.length()];
		String answer ="";
		int a = start.length();
		int b = end.length();
		while(true)
		{
			Pair oldTrace = backTrace;
			backTrace = backPointer[oldTrace.x][oldTrace.y];
			String verdict = "";
		
			if(oldTrace.verdict == insertId)
			{
				verdict = "insert";
				answer = "I" + end.charAt(b-1) + String.format("%02d", b) + answer;

			}
			else if(oldTrace.verdict == deleteId)
			{
				verdict = "delete";
				answer = "D" + start.charAt(a-1) + String.format("%02d", b+1) + answer;
			}
			else if(oldTrace.verdict == matchId)
			{
				verdict = "match";
			}
			else if(oldTrace.verdict == mismatchId)
			{
				verdict = "mismatch";
				answer = "C" + end.charAt(b-1) + String.format("%02d", b) + answer;
			}

			a = oldTrace.x;
			b = oldTrace.y;

			if(oldTrace.hasConverged())
				break;
		}

		answer = answer + "E";
		// if(validate(start, answer, end))
		// {
		// 	System.out.println("YES");
		// }
		// else
		// {
		// 	System.out.println("NO");
		// }
		out.println(answer);
	}


	public boolean validate(String start, String answer, String end)
	{

		String groups[] = answer.split("\\s+");
		String result = new String(start);
		for(int a =0;a < groups.length; a++)
		{
			if(!groups[a].isEmpty() && groups[a].charAt(0) != 'E')
			{
				char operation = groups[a].charAt(0);
				char character = groups[a].charAt(1);
				int number = Integer.parseInt(groups[a].substring(2,4));
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
		}
//		System.out.println("Result is " + result);
		return result.equals(end);
	}



}