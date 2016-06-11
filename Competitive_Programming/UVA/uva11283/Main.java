import java.io.*;
import java.lang.*;
import java.text.*;
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


	public boolean isInteger(String s) {
	    return s.matches("^-?\\d+$");
	}


	char matrix[][];
	int colNums;
	int rowNums;
	String query;
	List<Pair> needToCheck;
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(new FileInputStream(args[0]));
		out = new PrintWriter(System.out, true);
		int numTestCases = scanner.nextInt();
		int copyNumTestCases = numTestCases;
		scanner.nextLine();
		matrix = new char[2000][2000];
		needToCheck = new ArrayList<Pair>();
		needToCheck.add(new Pair(-1,0));
		needToCheck.add(new Pair(-1,1));
		needToCheck.add(new Pair(0,1));
		needToCheck.add(new Pair(1,1));
		needToCheck.add(new Pair(1,0));
		needToCheck.add(new Pair(1,-1));
		needToCheck.add(new Pair(0,-1));
		needToCheck.add(new Pair(-1,-1));
		while(copyNumTestCases-- >0)
		{
			scanner.nextLine();
			int numQueries = 0;
			rowNums = 0;
			colNums = 0;

			while(scanner.hasNextLine())
			{
				String str = scanner.nextLine();
				str = str.trim();
				if(isInteger(str))
				{
					numQueries = Integer.parseInt(str);
					break;
				}
				else
				{
					for(int a =0; a < str.length(); a++)
					{
						matrix[rowNums][a] = str.charAt(a);
					}
				}
				colNums = str.length();
				rowNums++;
			}
			while(numQueries-- > 0 )
			{
				query = scanner.nextLine();
				query = query.trim();

				out.println("Score for Boggle game #" + t + ": " + runQuery());
			}

		}
	}

	class Pair 
	{
		int x;
		int y;
		public Pair(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}

	public int score(String s) {
		int N = s.length();
		if (N == 3 || N == 4) return 1;
		if (N == 5) return 2;
		if (N == 6) return 3;
		if (N == 7) return 5;
		if (N >= 8) return 11;
		return 0;
	}

	public void runQuery()
	{



	}





}