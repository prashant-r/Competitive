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
	int x;
	int y;
	char matrix[][];
	int rowLength;
	int colLength;
	List<Pair> needToCheck;
	PrintWriter out;
	String query;
	public void run(String args[]) throws Exception
	{
		needToCheck = new ArrayList<Pair>();
		needToCheck.add(new Pair(-1,0));
		needToCheck.add(new Pair(-1,1));
		needToCheck.add(new Pair(0,1));
		needToCheck.add(new Pair(1,1));
		needToCheck.add(new Pair(1,0));
		needToCheck.add(new Pair(1,-1));
		needToCheck.add(new Pair(0,-1));
		needToCheck.add(new Pair(-1,-1));
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		int testCases = scanner.nextInt();
		int copyTestCases = testCases;
		scanner.nextLine();
		scanner.nextLine();
		while(copyTestCases-- >0)
		{
			int numberRows = scanner.nextInt();
			int numberCols = scanner.nextInt();
			scanner.nextLine();
			int rowNum = 0;	
			matrix = new char[2000][2000];
			while(numberRows-- > 0)
			{
				String row = scanner.nextLine();
				Scanner sc = new Scanner(row);
				String parsed = sc.nextLine();
				parsed = parsed.toUpperCase();
				for(int a = 0; a< numberCols; a++)
				{
					matrix[rowNum][a] = parsed.charAt(a);
				}
				rowNum++;
			}
			rowLength =rowNum;
			colLength = numberCols;
			int numQueries = scanner.nextInt();
			scanner.nextLine();
			while(numQueries --> 0)
			{
				query = scanner.nextLine();
				query = query.trim();
				query = query.toUpperCase();
				Pair result = exhaustiveSearch();
				out.printf("%d %d", result.x +1, result.y+1);

				out.println();
			}
			if(scanner.hasNextLine())
			{
				out.println();
				scanner.nextLine();
			}
		}	
	}

	class Pair
	{

		int x,y;
		public Pair(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

	}


	public Pair exhaustiveSearch()
	{ 
		
		Pair result = new Pair(Integer.MAX_VALUE, Integer.MAX_VALUE);
		needToCheck = new ArrayList<Pair>();
		needToCheck.add(new Pair(1,0));
		needToCheck.add(new Pair(-1,0));
		needToCheck.add(new Pair(0,1));
		needToCheck.add(new Pair(0,-1));
		needToCheck.add(new Pair(1,-1));
		needToCheck.add(new Pair(-1,-1));
		needToCheck.add(new Pair(-1,1));
		needToCheck.add(new Pair(1,1));
		for(int a =0; a< rowLength; a++)
		{
			for(int b= 0; b < colLength; b++)
			{
				
				if(matrix[a][b] == query.charAt(0))
				{
					for(Pair direction: needToCheck)
					{
					if(exhaustiveSearch(matrix[a][b] + "", a, b, 1, direction))
					{

						if(a == result.x)
						{
							if(b < result.y)
							{
								result.x = a;
								result.y = b;
							}							
						}
						else if(a < result.x)
						{
							result.x = a;
							result.y = b;							
						}
					}
					}
					
				}	

				
			}
		}

		return result;
	}
	public boolean exhaustiveSearch(String formed, int x, int y, int matchedSoFar, Pair mustCheck)
	{

		if(matchedSoFar == query.length())
		{
			if(formed.equals(query))
			{
				return true;
			}
			else
				return false;
		}
		int xx =0;
		int yy = 0;
		boolean result = false;
		
		int reachedOut = check(mustCheck.x, mustCheck.y, x,y,matchedSoFar);

		xx = x + mustCheck.x;
        yy = y + mustCheck.y;
		if(reachedOut == 1)
		{
			result  = exhaustiveSearch(formed + matrix[xx][yy], xx, yy,matchedSoFar+1, mustCheck);	
		}			
		return result;
	}


	public int check(int dx, int dy, int x, int y,int matchedSoFar) {
        int xx=0, yy=0;
        xx = dx + x;
        yy = dy + y;
        if (xx < rowLength && xx >= 0 && yy < colLength && yy >= 0)
        {

        		if(matrix[xx][yy] == query.charAt(matchedSoFar))
        		{
        			return 1;
        		}

        }

        	return -1; 
        }


}