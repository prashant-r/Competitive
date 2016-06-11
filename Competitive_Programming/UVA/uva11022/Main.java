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
	int matrix[][];
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while(true)
		{
			
			String str = scanner.nextLine();
			if(str.equals("*"))
				break;
			out.println(getStringFactoring(str));
		}
	}

	public int getStringFactoring(String str)
	{	
		matrix = new int[str.length()][str.length()];


		for(int a = 0; a<str.length() ; a++)
		{
			matrix[a][a] = 1;
		}

		int mul = -1;
		for(int a = 1 ; a<str.length(); a++)
		{
			for(int b = 0; b+a<str.length(); b++)
			{
				int c= b+a;      
				if((mul = allRep(str.substring(b, c+1))) != -1)
				{
          if(mul == 1)
            matrix[b][c] = 1;
          else
          {
					  matrix[b][c] = matrix[b][b+mul-1];
          }
				}
        else
        {
        int min = 100000;
				for(int k = b; k < c; k++)
        {
          min = Math.min(matrix[b][k] + matrix[k+1][c], min);
        }
        matrix[b][c] = min;
        }
			}
		}

		return matrix[0][str.length()-1];
	}

	public int allRep(String str)
	{

		int div = -1;
		for(int a =1 ; a <= str.length()/2; a++)
		{
			int pivot = a;
			int start = 0;

			boolean satisfied = true;
			while(pivot + a <= str.length())
			{
				if(!str.substring(start, pivot).equals(str.substring(pivot, pivot+a)))
				{
 					satisfied = false;
				}
        start = pivot;
				pivot = pivot + a;

			}
			if(satisfied && pivot == str.length())
      {
				div = a;
        break;
      }
		}
 		return div;
	}
}