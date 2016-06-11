import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.*;

// Accepted on UVA :)
// Took soo long to get this right not even funny ~ 5 hours 

class Main
{
	public Main()
	{


	}
	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}


	int matrix[][];
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		int numTestCases = scanner.nextInt();
		scanner.nextLine();
		
		while(numTestCases--> 0)
		{
			int t = scanner.nextInt();
			String str = scanner.next();
			matrix = new int[1000][26];
			if(scanner.hasNextLine())
				scanner.nextLine();
			out.println(minChunks(t,str));
		}

	}

	public int minChunks(int t, String query)
	{
		int minValFromLast = 1000000;
		int index =-1;
		int start = 0;
		int u = 0;
		for(int j = t; j<=query.length(); j+=t)
		{
			int minFromLast = 1000000;
			u++;
			String str = query.substring(start, j);
			Set<Character> processed = new HashSet<Character>();
			for(int a =0 ; a< str.length(); a++)
			{
				index = a;
				if(processed.contains(str.charAt(index)))
					continue;
				processed.add(str.charAt(index));
				int min = 1000000;
				

				
				String goo = str.substring(0, index) + str.substring(index+1, str.length());	
				
				Set<Character> headProcessed = new HashSet<Character>();
				if(goo.length() == 0)
				{
					min = Math.min(min,1+ ((u>1) ? (((matrix[u-1][str.charAt(index)-'a'] !=0)  ? matrix[u-1][str.charAt(index)-'a'] -1 : minValFromLast)):0));
				}
				else
				{
				
					for(int x =0 ; x<goo.length(); x++)
					{
						if(headProcessed.contains(goo.charAt(x)))
								continue;
						headProcessed.add(goo.charAt(x));
						String fetch = goo.charAt(x) + goo.substring(0,x) + goo.substring(x+1,goo.length()); 
						min = Math.min(min, costOfString(fetch + str.charAt(index) ) + ((u>1) ? (((matrix[u-1][goo.charAt(x)-'a'] !=0)  ? matrix[u-1][goo.charAt(x)-'a'] -1 : minValFromLast)):0));
					}
				}
				if(min < minFromLast)
					minFromLast = min;
				matrix[u][str.charAt(index) - 'a'] = min;
			}
			minValFromLast = minFromLast;
			start = j;
		}
		return minValFromLast;
	}

	public int costOfString(String f)
	{

		boolean allEqual = true;
		char start = f.charAt(0);
		for(int h = 1; h< f.length(); h++)
		{
			if(f.charAt(h) != start)
			{
				allEqual = false;
			}
		}
		if(allEqual)
			return 1;
		else
		{
			int count = 0;
			String inBetween = f.substring(1, f.length()-1);

			HashMap<Character, Integer> chars = new HashMap<Character, Integer>();
			for(int h = 0; h< inBetween.length(); h++)
			{
				if(chars.containsKey(inBetween.charAt(h)))
					chars.put(inBetween.charAt(h), chars.get(inBetween.charAt(h))+1);
				else
					chars.put(inBetween.charAt(h), 1);
			}
			count = chars.size();
			if(!chars.containsKey(f.charAt(0)))
			{
				count = count+1;
			}
			else
			{
				int numRet = chars.get(f.charAt(0));
				if(numRet == 0)
				{
					count = count+1;
				}
				else
				{
					chars.put(f.charAt(0), 0);
				}
			}
			if(!chars.containsKey(f.charAt(f.length()-1)))
			{
				count = count+1;
			}
			else
			{
				int numRet = chars.get(f.charAt(f.length()-1));
				if(numRet == 0)
				{
					count = count+1;
				}
				else
				{
					chars.put(f.charAt(f.length()-1), 0);
				}

			}
			return count;
		}
	}

}