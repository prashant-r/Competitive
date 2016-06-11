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

	int matrix[][];
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		int numTestCases = scanner.nextInt();
		scanner.nextLine();
		while(numTestCases-- > 0)
		{
			String str = scanner.nextLine();
			matrix = new int[str.length()][str.length()];
			out.println(longestPalindrome(str));
		}
	}


	public int longestPalindrome(String str)
	{
		if(str.isEmpty())
		{
			return 0;
		}

		for(int c = 0; c< str.length(); c++)
			matrix[c][c] = 1;

		for(int c = 0; c < str.length()-1; c++)
		{
			if(str.charAt(c) == str.charAt(c+1))
				matrix[c][c+1] = 2;
			else
				matrix[c][c+1] = 1;
		}

		for(int a = 2; a < str.length(); a++)
		{
			for(int i = 0; i+a<str.length(); i++ )
			{
				int j = i +a;

				if(str.charAt(i) == str.charAt(j))
				{
					//if(j-1 - (i+1) +1 == matrix[i+1][j-1] then the whole substring is palindrome )
					// so then we are limiting ourselves to finding the largest palindrome in the given string
					// but we are allowed to delete strings and hence largest palindrome in range(i+1, j-1) can be sought 
					// and the letters not belonging to this palindrome but are in the aforementioned range can be deleted.
					matrix[i][j] = 2+ matrix[i+1][j-1];
				}
				else
				{
					matrix[i][j] = Math.max(matrix[i+1][j], matrix[i][j-1]);
				}
			}

		}
		return matrix[0][str.length()-1];
	}
}