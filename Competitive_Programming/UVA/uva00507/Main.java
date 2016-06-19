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
	int caseNo = 0;
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while(scanner.hasNextLine())
		{

			int numTest = scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			int copyNumTest = numTest;
			int arr[];
			while(copyNumTest-- > 0)
			{
				caseNo++;
				int x = scanner.nextInt();
				if(scanner.hasNextLine())
					scanner.nextLine();
				x = x-1;
				int copyx = x ;
				arr = new int[x];
				while(copyx-- > 0)
				{
					int d = scanner.nextInt();
					if(scanner.hasNextLine())
						scanner.nextLine();
					arr[x-copyx-1] = d;					
				}	
				findBestRoute(arr);			
			}
		}
	}

	public void findBestRoute(int arr[])
	{
		int MSS = 0;
		int Start = Integer.MIN_VALUE;
		int Max_Start= Integer.MIN_VALUE;
		int Max_End = Integer.MIN_VALUE;
		int Max = 0;
		for (int j = 1; j <= arr.length; ++j){
			int num = arr[j-1];
            if (MSS >= 0)
                MSS += num;
            else {
                MSS = num;
                Start = j;
            }

            if (MSS > Max || (MSS == Max && j+1 - Start > Max_End - Max_Start))
            {
                Max = MSS;
                Max_Start = Start;
                Max_End = j + 1;
            }
        }
        if(Max == 0)
        {
			out.printf("Route %d has no nice parts\n", caseNo);        	
        }
       	else
        	out.printf("The nicest part of route %d is between stops %d and %d\n", caseNo, Max_Start, Max_End);
	}
}