	import java.io.*;
	import java.math.*;
	import java.util.*;

	// Accepted on UVA :)

	class Main
	{
		public Main()
		{
		}

		char charSet[] = new char[100];
		public static final int MAX_SIZE = 2150000;
		public static int tempRA[] = new int[MAX_SIZE];
		public static int tempSA[] = new int[MAX_SIZE];
		public static int SA[] = new int[MAX_SIZE];
		public static int RA[] = new int[MAX_SIZE];
		public static int Phi[] = new int[MAX_SIZE];
		public static int PLCP[] = new int[MAX_SIZE];
		public static int LCP[] = new int[MAX_SIZE];
		public static int c[] = new int[MAX_SIZE];
		public static char T[] = new char[MAX_SIZE];
		public static int n ;
	 	public static String query;
	 	public static int population;
	 	public static int stringBreakPoints[];
	 	public static int maxN;
	 	public static int maxLCP;

		public static void main(String args[]) throws Exception
		{
			new Main().run(args);
		}
		public void run(String args[]) throws Exception 
		{

			Scanner scanner = new Scanner(System.in);
			for(int a = 200; a< 300; a++)
			{
				charSet[a-200] = (char)a;
			}
			boolean started = false;
			while(scanner.hasNextLine())
			{
				population = scanner.nextInt();
				if(scanner.hasNextLine())
					scanner.nextLine();
				if(population == 0)
					break;
				else
				{
					if(started)
						System.out.println();
				}
				started = true;
				maxN = -1;
				int copyPopulation = population;
				stringBreakPoints = new int[population];
				query = "";
				while(copyPopulation-- > 0 )
				{
					String str = new String(scanner.nextLine());
					maxN = Math.max(str.length(), maxN);
					query += str + charSet[population-copyPopulation-1];
					stringBreakPoints[population-copyPopulation-1] = query.length()-1;

				}
				if(population  > 1)	
					longestCommonSubstring();
				else
					System.out.println(query.substring(0,stringBreakPoints[0]));
			}

		}

		public void longestCommonSubstring()
		{
			n = query.length();
			constructSuffixArray();
			constructPhiArray();
			constructLCPArray();
			bisectionMethod();
		}

		public class Candidate
		{
			Set<String> set;
			public Candidate(Set<String> set)
			{
				this.set= set;
			}
		}

		/*
		Locate the first number such that atleast half is not found
		*/
		public void bisectionMethod()
		{
			
			int low = 0; 
			int high = maxLCP+1;
			Candidate arr[] = new Candidate[n];
			while(low < high)
			{
				int mid = (low+ high)/2;
				arr[mid] = new Candidate((atleastHalfFound(mid)));
				if((arr[mid].set.isEmpty()))
				{
					high = mid;
				}
				else
				{
					low = mid+1;
				}
			}
			if(low == 0 || arr[low -1].set.isEmpty())
			{
				System.out.println("?");
			}
			else
			{
					for(String s: arr[low-1].set)
					{
						System.out.println(s);
					}
			}
		}

		public Set<String> atleastHalfFound(int length)
		{
			String maxLString = "";
			Set<String> setofLength = new  HashSet<String>();
			HashMap<String, Set<Integer>> hash = new HashMap<String, Set<Integer>>();
			int maxL = 0 ;
			for(int a= 1 ; a< n ; a++)
			{
				maxL = LCP[a];
				if(maxL >= length)
				{
				int prev = binarySearch(SA[a-1]);
				int curr = binarySearch(SA[a]);
				maxLString = query.substring(SA[a], SA[a] + length);
				if(!hash.containsKey(maxLString))
				{
					Set set = new HashSet<Integer>();
					set.add(prev);
					set.add(curr);
					hash.put(maxLString, set);
				}
				else
				{
					hash.get(maxLString).add(prev);
					hash.get(maxLString).add(curr);
				}
				setofLength.add(maxLString);
				}
			}
			Set<String> answer = new TreeSet<String>();
	       	for(String s : setofLength)
		 		{

		 			if(s.isEmpty()) continue;
		 			if(hash.get(s).size() > population/2)
			 			{
			 				answer.add(s);
			 			}
				}
			return answer;

		}


		int binarySearch(int a)
		{
			int low = 0;
			int high = stringBreakPoints.length-1;
			while(low < high)
			{
				int mid = (low + high)/2;

				if(stringBreakPoints[mid]>= a)
				{
					high = mid;
				}
				else
				{
					low = mid + 1;
				}
			}
			return low;
		}


		class StringCount implements Comparable<String>
		{
			String str; 
			int count;

			public StringCount(String str, int count)
			{
				this.str = str;
				this.count = count;
			}

			@Override
			public int compareTo(String s)
			{
				return this.compareTo(s);
			}

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
			maxLCP = -1;
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
				maxLCP = Math.max(LCP[a], maxLCP);
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