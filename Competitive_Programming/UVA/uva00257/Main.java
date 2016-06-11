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
	boolean matrix[][];
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while(scanner.hasNextLine())
		{
			String words[] = scanner.nextLine().split("\\s+");
			for(int a= 0; a< words.length; a++)
			{
				if(processWord(words[a]))
					out.println(words[a]);
			}

		}
	}

	
	public boolean processWord(String word)
	{
		matrix = new boolean[word.length()][word.length()];

		String found = "";
		int x = -1;
		int y = -1;

		for(int a= 0; a < word.length(); a++)
		{
			matrix[a][a] = true;
		}

		for(int a= 0; a < word.length() -1; a++)
			if(word.charAt(a) == word.charAt(a+1))
				matrix[a][a+1] = true;

		for(int q = 2; q < word.length(); q++)
		{
			for(int i = 0 ; i+q <word.length(); i++ )
			{

				int j = i+q;
				if(word.charAt(i) == word.charAt(j))
				 {
				 	if (matrix[i+1][j-1] == true)
				 	{
				 		matrix[i][j] = true;
				 		if(found.isEmpty())
				 		{	
				 			found = word.substring(i,j+1);
				 			x = i;
				 			y = j;
				 		}
				 		else
				 		{
				 			//System.out.println(found + " " + word.substring(i,j+1));
				 			if( i >x || j < y )
				 			{
				 				if(word.substring(i,j+1).indexOf(found, 0) == -1)
				 				{
				 					return true;
				 				}
				 			}
				 		}
				 	}
				 }

			}
		}
		return false;
	}

}