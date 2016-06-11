import java.io.*;
import java.math.*;
import java.util.*;

// Accepted on UVA :)

class Main
{
	
	public Main()
	{

		int MAX_SIZE = 100010;
		tempRA = new int[MAX_SIZE];
		tempSA = new int[MAX_SIZE];
		SA = new int[MAX_SIZE];
		RA = new int[MAX_SIZE];
		T = new char[MAX_SIZE];
		Phi = new int[MAX_SIZE];
		LCP = new int[MAX_SIZE];
		PLCP = new int[MAX_SIZE];
	}

	int tempRA[];
	int tempSA[];
	int SA[];
	int RA[];
	int Phi[];
	int PLCP[];
	int LCP[];
	int c[];
	char T[];
	int n ;
 	String query;
	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}
	PrintWriter out;
	public void run(String args[]) throws Exception 
	{

		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		int numTestCases= scanner.nextInt();
		scanner.nextLine();
		int copyNumTestCases = numTestCases;
		while(copyNumTestCases-->0)
		{
			query = scanner.nextLine();
			out.println(longestRepeatedSubstring());
		}

	}

	public int longestRepeatedSubstring()
	{
		int answer = 0; 
		query = query + "$";
		n = query.length();
		constructSuffixArray();
		constructPhiArray();
		constructLCPArray();

		int maxL = 0 ;
		for(int a= 0 ; a< n ; a++)
		{
			if(LCP[a] > maxL)
			{
				maxL = LCP[a];
			}
		}
		answer = maxL;
		return answer;
	}

	public void constructPhiArray()
	{	
		Phi[SA[0]] = -1;
		for(int a = 1; a< n ; a++)
		{
			Phi[SA[a]] = SA[a-1];
		}
	}

	public void constructLCPArray()
	{
		int L = 0; 
		for(int a = 0; a< n ; a++)
		{
			if(Phi[a] == -1) {PLCP[a] = 0; continue;}; 
			while( T[a+L] == T[Phi[a] + L] ) L++;
			PLCP[a] = L;
			L = Math.max(L-1, 0);
		}
		for(int a = 0; a< n ; a++)
		{
			LCP[a] = PLCP[SA[a]];
		}
	}

	public void constructSuffixArray()
	{
		for(int a =0 ; a < n ;a++)
		{
			T[a] = query.charAt(a);
			RA[a] = T[a];
			SA[a] = a;
		}
		for(int k = 1 ; k< n ; k <<= 1)
		{
			countingSort(k);
			countingSort(0);
			int rank = 0;
			tempRA[SA[0]] = rank;
			for(int a= 1 ; a< n ; a++)
			{
				tempRA[SA[a]] = (RA[SA[a]] == RA[SA[a-1]] && RA[SA[a] + k] == RA[SA[a-1] + k]) ? rank : ++rank;
			}
			for(int i = 0 ; i < n ; i++)
			{
				RA[i] = tempRA[i];
			}
			if(RA[SA[n-1]] == n-1 ) break;
		}

	}

	public void countingSort(int k)
	{
		int maxi = Math.max(n, 300);
		c = new int[maxi];
		for(int a = 0 ; a< n ; a++)
		{
			c[SA[a]+k < n ? RA[SA[a]+k] : 0]++;
		}
		int sum = 0;
		for(int i = 0; i < maxi; i++)
		{
			int oldsum = c[i];
			c[i] = sum;
			sum += oldsum;
		}

		for(int a = 0; a < n ; a ++)
		{
			tempSA[c[SA[a]+k < n ? RA[SA[a]+k] : 0]++] = SA[a];
		}

		for(int a= 0; a < n ; a++)
		{
			SA[a] = tempSA[a];
		}

	}


}