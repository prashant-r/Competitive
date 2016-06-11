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

	int n;
	int MAX_N = 100100;
	char T[] = new char[MAX_N];
	int SA[] = new int[MAX_N];
	int tempSA[] = new int[MAX_N];
	int RA[] = new int[MAX_N];
	int tempRA[] = new int[MAX_N];
	int c[];

	public void countingSort(int k)
	{
		int i, sum , maxi = Math.max(300, n);
		c = new int[MAX_N];
		for(i = 0; i < n ; i++)
		{
			c[SA[i]+k < n ? RA[SA[i] + k] : 0]++;
		}
		sum = 0;
		for(i = 0; i < maxi; i++)
		{
			int oldSum = c[i];
			c[i] = sum;
			sum+=oldSum;
		}
		for(i = 0; i < n ; i++)
			tempSA[c[SA[i] + k < n ? RA[SA[i] + k] : 0]++] = SA[i];
		for(i =0; i < n; i++)
			SA[i] = tempSA[i];
	}


	public void constructSA()
	{
		for(int i = 0; i < n; i++)
		{
			RA[i] = (int) T[i];
		}
		for(int i = 0; i < n ; i++)
			SA[i] = i;
		for(int k = 1; k < n ; k<<=1)
		{
			countingSort(k);
			countingSort(0);
			for(int i =0 ; i<n ; i++)
			tempRA[SA[0]] = 0;
			int rank = 0;

			for(int i =1 ; i < n; i++)
				tempRA[SA[i]] = (RA[SA[i-1]] == RA[SA[i]] && RA[SA[i] + k] == RA[SA[i-1] + k]) ? rank : ++rank;
			
			for(int i =0; i < n; i++)
				RA[i] = tempRA[i];
			if(RA[SA[n-1]] == n-1) break;
		}
	}


	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		int numTestCases = scanner.nextInt();
		scanner.nextLine();
		int copyNumTestCases = numTestCases;
		while(copyNumTestCases-- > 0)
		{	
			String str = new String(scanner.nextLine());
			String rstr = str;
			String lstr = str;
			int originalLength = rstr.length();
			str = lstr + rstr;
			str = str + "}";
			n = str.length();
			for(int a = 0; a< n ; a++)
			{
				T[a] = str.charAt(a);
			}	

			constructSA();
			
			for(int b = 0; b < n ; b++)
				if(SA[b] < originalLength)
				{
					out.println(SA[b]+1);
					break;
				}
		}

	}
}