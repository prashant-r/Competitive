import java.io.*;
import java.math.*;
import java.util.*;

// Need to complete .. 

class Main
{
	
	int modulo = 1000000009;
	public Main()
	{

	}
	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}
	PrintWriter out;
	int bitmask;
	Pair numWays[];
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(new FileInputStream(args[0]));
		out = new PrintWriter(System.out,true);
		while(scanner.hasNextLine())
		{
			int n = scanner.nextInt();
			scanner.nextLine();
			int m = scanner.nextInt();
			scanner.nextLine();
			bitmask = 1<<m ;
			numWays = new Pair[bitmask];
			String a = scanner.nextLine();
			String b = scanner.nextLine();
			if(scanner.hasNextLine())
				scanner.nextLine();
			out.println(countBoards(n,m, a, b));
			

		}

	} 

	class Pair
	{
		int selected_nways;
		int not_selected_nways;

		public Pair(int selected_nways, int not_selected_nways)
		{
			this.selected_nways = selected_nways %modulo;
			this.not_selected_nways = not_selected_nways %modulo;			
		}
	}

	public int countBoards(int n, int m , String a, String b)
	{

		int answer =0;
		for(int i = 0; i < n ; i++)
		{
			for(int j = 0; j < m ; j++)
			{
				for(int x = 0; x < bitmask; x++)
				{
					if(a.charAt(i+j) == b.charAt(i+j))
					{
						out.println("Who");
					}

				}
			}
		}
		return answer;
	}
}

