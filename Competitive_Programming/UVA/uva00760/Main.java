import java.io.*;
import java.math.*;
import java.util.*;

// Accepted on UVA :)

class Main
{
	public Main()
	{
		Phi = new int[MAX_INT];
		PLCP = new int[MAX_INT];
		LCP = new int[MAX_INT];
		SA = new int[MAX_INT];
		RA = new int[MAX_INT];
		T = new char[MAX_INT];
		tempSA = new int[MAX_INT];
		tempRA = new int[MAX_INT];
	}
	public static void main(String args[]) throws Exception 
	{
		new Main().run(args);
	}
	public final int MAX_INT = 100100;
	int Phi[];
	int PLCP[];
	int LCP[];
	int SA[];
	int RA[];
	int c [];
	char T[];
	int tempSA[];
	int tempRA[];
	int n;
	String query;
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while(scanner.hasNextLine())
		{
			String str1 = scanner.nextLine();
			String str2 = scanner.nextLine();
			char str1EndSymbol = '{';
			char str2EndSymbol = '}';
			str1 = str1 + str1EndSymbol;
			int p = str1.length();
			str2 = str2 + str2EndSymbol;
			query = str1 + str2;
			n = query.length();
			constructSuffixArray();
			constructPhiArray();
			computeLCP();
			int maxCP = 0;
			Set<String> answer = new TreeSet<String>();
			for(int y = 1; y < n ; y++)
			{
				if(SA[y] < p-1  &&  SA[y-1] >= p || SA[y] >=p && SA[y-1] < p-1)
				{
					if(LCP[y] > maxCP)
					{
						maxCP = LCP[y];
					}
				}
			}
			boolean found = false;
			if(maxCP > 0)
			{
				found = true;
				for(int y = 1; y < n ; y++)
				{
					if(SA[y] < p-1  &&  SA[y-1] >= p || SA[y] >=p && SA[y-1] < p-1)
					{
						if(LCP[y] == maxCP)
						{
							String maxCPStr = query.substring(SA[y], SA[y] + LCP[y]);
							answer.add(maxCPStr);
						}
					}
				}

			}
			else
			{
				found = false;
			}
			if(!found)
				out.println("No common sequence.");
			else
			{
				for(String ans: answer)
				{
					out.println(ans);
				}
			}
			if(scanner.hasNextLine())
			{
				out.println();
				scanner.nextLine();
			}
			
		}
	}


	public void constructSuffixArray()
	{
		for(int a = 0; a< n; a++)
		{
			T[a] = query.charAt(a);
			RA[a] = T[a];
			SA[a] = a;
		}
		for(int k =1; k < n; k <<=1)
		{
			countingSort(k);
			countingSort(0);
			int rank = 0;
			tempRA[SA[0]] = rank;
			for(int i =1 ; i < n; i++)
			{
				tempRA[SA[i]] = (RA[SA[i-1]] == RA[SA[i]] && RA[SA[i-1] + k ]== RA[SA[i] + k ] ) ? rank : ++rank;
			}
			for(int a = 0; a < n ; a++)
			{
				RA[a] = tempRA[a];
			}
			if(RA[SA[n-1]] ==n-1) break;
		}

	}

	public void constructPhiArray()
	{
		Phi[SA[0]] = -1;
		for(int i = 1; i < n ; i++)
		{
			Phi[SA[i]] = SA[i-1];
		}
	}


	public void computeLCP()
	{
		int L = 0;
		for(int i = 0; i < n ; i++)
		{
			if(Phi[i] == -1) {PLCP[i] = 0; continue; };
			while(query.charAt(i + L) == query.charAt(Phi[i] + L)) L++;
			PLCP[i] = L;
			L = Math.max(L-1, 0);
		}
		for(int i = 0; i < n ; i++)
		{
			LCP[i] = PLCP[SA[i]]; 
		}
	}

	public void countingSort(int k)
	{
		int maxi = Math.max(400, n);
		int sum = 0; 
		c = new int[MAX_INT];
		for(int i = 0; i < n; i++)
		{
			c[SA[i] + k < n ? RA[SA[i] + k] : 0]++;
		}
		for(int i = 0; i < maxi; i++)
		{
			int oldSum = c[i];
			c[i] = sum;
			sum += oldSum;
		}
		for(int i = 0; i < n; i++)
		{
			tempSA[c[SA[i] + k < n ? RA[SA[i] + k] : 0]++] = SA[i];
		}
		for(int i = 0; i < n ; i++)
		{
			SA[i] = tempSA[i];
		}
	}


}