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

	long matrix[][];
	PrintWriter out;
	String query;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		int numTestCases = scanner.nextInt();
		scanner.nextLine();
		while(numTestCases-- > 0)
		{	
			query = scanner.nextLine();
			matrix = new long[query.length()+1][query.length()+1];
			for (int i = 0; i <= query.length(); ++i)
				Arrays.fill(matrix[i], -1);
			out.println(findLargestNumber(0, query.length()-1));

		}
	}

	long findLargestNumber(int l, int r) {
		if (l > r) return 0;
		
		if (matrix[l][r] != -1) return matrix[l][r];
		
		long ans = 0;
		for (int i = l + 1; i <= r + 1; ++i) {
			String str = query.substring(l, i);
			if (str.length() > 1 && str.charAt(0) == '0') continue;
			
			long num = Long.parseLong(str);
			if (num >= Integer.MAX_VALUE) break;
			
			ans = Math.max(ans, num + findLargestNumber(i, r));
		}
		
		return matrix[l][r] = ans;
	}
}