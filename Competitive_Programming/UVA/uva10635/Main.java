	import java.io.*;
	import java.util.*;
	import java.math.*;
	import java.lang.*;

	// Accepted on UVA:)

	// Classic LCS solution times out.
	// Use LIS solution instead O(nlogn) 

	class Main
	{
		public Main()
		{


		}
		public static void main(String args[]) throws Exception
		{
			new Main().run(args);
		}

		class Pair implements Comparable<Pair>
		{
			int index;
			int value;

			public Pair(int value, int index)
			{
				this.index = index;
				this.value = value;
			}

			public int compareTo(Pair x)
	    	{
	    		return Integer.valueOf(this.value).compareTo(x.value);
	    	}
	    	@Override
	    	public String toString()
	    	{
	    		return "index " + index + " " + "value "  + value;
	    	}

		}

		int arr1[];
		int arr2[];
		Pair arrSorted [];
		int arrUnSorted [];
		boolean choice = false;
		PrintWriter out;
		int forLis[];
		public void run(String args[]) throws Exception
		{
			Scanner scanner = new Scanner(System.in);
			out = new PrintWriter(System.out, true);
			int numTestCases = scanner.nextInt();
			int copyNumTestCases = numTestCases;
			if(scanner.hasNextLine())
				scanner.nextLine();
			while(copyNumTestCases-- >0)
			{
				int n = scanner.nextInt();
				int p = scanner.nextInt();
				int q = scanner.nextInt();
				p = p+1;
				q = q+1;

				int copyp = p;
				int copyq = q;
				arr1 = new int[p];
				arr2 = new int[q];
				if(p < q)
				{
					choice = true;
					arrUnSorted = arr2;
					arrSorted = new Pair[p];
					forLis = new int[p];
				}
				else
				{
					choice = false;
					arrUnSorted = arr1;
					arrSorted = new Pair[q];
					forLis = new int[q];
				}
				if(scanner.hasNextLine())
					scanner.nextLine();
				while(copyp-- > 0)
				{
					arr1[p-copyp-1] = scanner.nextInt();
					if(choice)
					{
						arrSorted[p-copyp-1] = new Pair(arr1[p-copyp-1], p-copyp-1);
					}
				}
				if(scanner.hasNextLine())
					scanner.nextLine();
				while(copyq-- > 0)
				{
					arr2[q-copyq-1] = scanner.nextInt();
					if(!choice)
					{
						arrSorted[q-copyq-1] = new Pair(arr2[q-copyq-1], q-copyq-1);
					}
				}
				Arrays.sort(arrSorted);
				if(scanner.hasNextLine())
					scanner.nextLine();
				out.println("Case " + (numTestCases-copyNumTestCases) +  ": " + LCS());
			}
		}

		public int LCS()
		{
			for(int a = 0; a< arrUnSorted.length; a++)
			{
				int mid = binarySearch(arrUnSorted[a],0, arrSorted.length-1, arrSorted);
				if(arrSorted[mid].value == arrUnSorted[a])
				{
					forLis[arrSorted[mid].index] = a+1;
				}
			}
			return LIS();
		}

		public int binarySearch(int a, int low, int high, Pair arr[])
		{

			int mid = 0;
			while(low <= high)
			{
				mid = (low + high)/2;
				if(arr[mid].value < a)
				{
					low = mid+1;
				}
				else if(arr[mid].value >a)
				{
					high = mid-1;
				}
				else
				{
					break;
				}
			}
			return mid;
		
		}

		int LIS()
		{

			return new LISFinder().LongestIncreasingSubsequenceLength(forLis, forLis.length);


		}

		class LISFinder
		{
			int CeilIndex(int A[], int l, int r, int key)
	    	{
	       	 while (r - l > 1)
	         {
	           	 int m = l + (r - l)/2;
	             if (A[m]>=key)
	                r = m;
	             else
	                l = m;
	         }
	 
	         return r;
	         }
	 
	    int LongestIncreasingSubsequenceLength(int A[], int size)
	    {
	 
	        int[] tailTable   = new int[size];
	        int len; 
	 
	        tailTable[0] = A[0];
	        len = 1;
	        for (int i = 1; i < size; i++)
	        {
	        	if(A[i] == 0)
	        	{
	        		continue;
	        	}
	            if (A[i] < tailTable[0])
	                tailTable[0] = A[i];
	 
	            else if (A[i] > tailTable[len-1])
	                tailTable[len++] = A[i];
	 
	            else
	                tailTable[CeilIndex(tailTable, -1, len-1, A[i])] = A[i];
	        }
	 
	        return len;
	    }
		}
	}