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
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		boolean started = false;
		out = new PrintWriter(System.out, true);
		while(scanner.hasNextLine())
		{
			int first = 0; 
			Scanner sc = new Scanner(scanner.nextLine());
			int n = -1;
			List<Integer> r = new ArrayList<Integer>();
			while(sc.hasNext())
			{
				int y = sc.nextInt();
				if(first == 0)
				{
					n = y;
					if(started && n !=0)
						out.println();
				}
				else
				{
					r.add(y);
				}
				first++;
			}
			findCombinations(n, r);
			started = true;
		}
	}


	public void findCombinations(int n, List<Integer> r)
	{
		for(int a = 0; a < n-5; a++)
		{
			for(int b =a+1; b < n-4; b++)
			{
				for(int c = b+1; c< n-3; c++ )
				{
					for(int d = c+1; d< n-2; d++)
					{
						for(int e = d+1; e< n-1; e++)
						{
							for(int f = e+1; f < n-0; f++ )
							{
								out.println(r.get(a)+ " " + r.get(b) + " " + r.get(c) + " " + r.get(d) +" " + r.get(e) + " " + r.get(f));
							}
						}
					}
				}
			}
		}

	}
}