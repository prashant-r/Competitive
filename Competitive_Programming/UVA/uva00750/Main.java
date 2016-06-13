import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.*;

// Accepted on UVA :)

// N-queen puzzle O(8!)

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
	int i, j;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		int numT = scanner.nextInt();
		while(numT-->0)
		{
			i = scanner.nextInt();
			j = scanner.nextInt();
			printNQueeBoardLayout();	
			if(scanner.hasNextLine())
			{	
				if(numT > 0)
					out.println();
				scanner.nextLine();
			} 
		}
	}

	public void printNQueeBoardLayout()
	{

		out.println("SOLN       COLUMN");
		out.println(" #      1 2 3 4 5 6 7 8\n");
		List<Set<Integer>> results = backtrack(1, new LinkedHashSet<Integer>()); 
		int counter = 0;
		for(Set<Integer> resultSet: results)
		{
			List<Integer> result = new ArrayList<Integer>(resultSet);
			int numberOfSpaces = 2;
			out.printf("%"+ numberOfSpaces+"d      %d %d %d %d %d %d %d %d\n",++counter, result.get(0), result.get(1), result.get(2), result.get(3), result.get(4), result.get(5), result.get(6), result.get(7));
		}
	} 

	public List<Set<Integer>> backtrack(int level, Set<Integer> set)
	{
		
		List<Set<Integer>> lists = new ArrayList<Set<Integer>>();
		
		if(level == 9)
		{
			if(set.size() == 8)
			{
				Set<Integer> newSet = new LinkedHashSet<Integer>(set);
				lists.add(newSet);
				return lists;
			}
			return lists;
		}

		for(int a = 1; a <= 8 ; a++)
		{
			if(canPlace(level, a, set))
			{
				set.add(a);
				lists.addAll(backtrack(level+1, set));	
				set.remove(a);
			}
		}
		return lists;
	}

	public boolean canPlace(int column, int row, Set<Integer> set)
	{
		if(column == j && row == i) return true;
		else if(column == j && row != i) return false;
		else if(row == i && column!=j) return false;
		else if( Math.abs(i - row ) == Math.abs((j- column))) return false;
		{

		boolean canPlaceIt = true;
		int colIndex = 1;
		for(Integer s : set)
		{
			int rowIndex = s;
			if(Math.abs(rowIndex - row ) == Math.abs((column - colIndex)))
			{
				canPlaceIt = false;
				break; 
			}
			else if( rowIndex == row || colIndex == column)
			{
				canPlaceIt = false;
				break;
			}
			colIndex++;
		}
		
		return canPlaceIt;
		}

	}



}