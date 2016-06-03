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
	boolean debug;
	char matrix1[][];
	char matrix2[][];
	char matrix[][];
	boolean visiteds[][];
	int rowLength1;
	int rowLength2;
	int rowLength;
	int colLength;
	String vowels = "AEIOUY";
	Set<Pair> needToCheck;
	public void run(String args[]) throws Exception
	{
		matrix1 = new char[1000][1000];
		matrix2 = new char[1000][1000];
		Scanner scanner = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out, true);
		int testCase = 0;
		while(scanner.hasNextLine())
		{
			String row = scanner.nextLine();
			if(row.isEmpty())
			{
				continue;
			}
			if(row.trim().equals("#"))
				break;
			Scanner sc = new Scanner(row);
			int rowNum = 0;
			while(true)
			{
				sc.useDelimiter("   ");
				for(int b = 0; b < 2; b++)
				{
					String parsed = sc.next();
					parsed = parsed.replaceAll("\\s+","");
					parsed = parsed.trim();
					for(int a = 0; a< parsed.length(); a++)
					{
						if(b == 0)
							matrix1[rowNum][a] = parsed.charAt(a);
						else
							matrix2[rowNum][a] = parsed.charAt(a);
					}
					if(b == 0)
						rowLength1 =parsed.length();
					else
						rowLength2 = parsed.length();
					
				}
				String nextLine = scanner.nextLine();
				sc = new Scanner(nextLine);
				rowNum++;
				if(nextLine.isEmpty())
				{
					
					if(testCase != 0)
						out.println();
					break;
				}
				
			}
			testCase++;
			colLength = rowNum;
			Set<String> answer = compare(exhaustiveSearch(0), exhaustiveSearch(1));
			if(answer.size() ==0 )
			{

				out.println("There are no common words for this pair of boggle boards.");
			}
			else
			{
			for(String str: answer)
				out.println(str);
			}
		}	
		
	}

	public Set<String> compare(Set<String> SetA, Set<String> SetB)
	{

		SetA.retainAll(SetB);
		return new TreeSet(SetA);

	}

	public Set<String> exhaustiveSearch(int which)
	{ 
		Set<String> result = new HashSet<String>();
		if(which == 0)
		{
			rowLength = rowLength1;
			matrix = matrix1;
		}
		else
		{
			rowLength = rowLength2;
			matrix = matrix2;
		}

		

		needToCheck = new HashSet<Pair>();
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
				visiteds = new boolean[colLength][rowLength];
				if(vowels.indexOf(matrix[a][b])!=-1)
					result.addAll(exhaustiveSearch(1, "" + matrix[a][b], a, b, visiteds));
				else
					result.addAll(exhaustiveSearch(0, "" + matrix[a][b], a, b, visiteds));
				
			}
		}

		return result;
	}

	public Set<String> exhaustiveSearch(int vowelCount, String formed, int x, int y, boolean visited[][])
	{
		if(formed.length() == 4)
		{
			if(vowelCount !=2)
			{
				Set<String> emptySet = new HashSet<String>();
				return emptySet;
			}
			else
			{

				Set<String> Set = new HashSet<String>();
				Set.add(formed);
				return Set;
			}
		}
		visited[x][y] = true;
		int xx =0;
		int yy = 0;
		Set<String> result = new HashSet<String>();
		for(Pair mustCheck: needToCheck)
		{
			int reachedOut = check(mustCheck.x, mustCheck.y, x,y, visited);

			xx = x + mustCheck.x;
        	yy = y + mustCheck.y;
        	if(reachedOut != -1)
        	{
        	
			if(reachedOut == 1)
			{
				result.addAll(exhaustiveSearch(vowelCount+1, formed + matrix[xx][yy], xx, yy, visited));
				
			}
			else if(reachedOut == 0)
			{
				result.addAll(exhaustiveSearch(vowelCount, formed + matrix[xx][yy],xx, yy, visited));
			}
			}
			else
			{
				continue;
			}
		}
		visited[x][y] = false;

		return result;
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

	public int check(int dx, int dy, int x, int y, boolean visited[][]) {
        int xx=0, yy=0;
        xx = dx + x;
        yy = dy + y;
        if (xx < rowLength && xx >= 0 && yy < colLength && yy >= 0)
        {
 			if(!visited[xx][yy])
        	{if(vowels.indexOf(matrix[xx][yy])!=-1)
          		return 1;
        	else
        		return 0;
        	}
        	else
        		return 2;

        }

        	return -1; 
        }


}