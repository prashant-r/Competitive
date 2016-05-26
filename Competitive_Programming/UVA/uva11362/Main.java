import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;

// Accepted on UVA :)

class Main
{
	public Main()
	{

	}
	int trieSize ;
	class Trie
	{
		boolean reachedEnd;
		Trie children[];
		public Trie()
		{
			this.reachedEnd = false;
			children = new Trie[10];
			for(int a =0; a <10; a++)
			{
				children[a] = null;
			}
		}
	}

	Trie tries ;
	String [] strs;
	public static void main(String args[]) throws Exception
	{
				new Main().run(args);
	}

	public boolean insertTrie(String str)
	{
		Trie trieToConsider =tries;
		for(int a= 0; a < str.length() ; a++)
		{
			if(trieToConsider.children[str.charAt(a) - '0'] == null)
			{
				trieToConsider.children[str.charAt(a) - '0'] = new Trie();
			}
			trieToConsider = trieToConsider.children[str.charAt(a) - '0'];
			if(trieToConsider.reachedEnd)
			{
				return true;
			}

		}
		for(int a = 0; a< 10; a++)
		{
			if(trieToConsider.children[a] != null)
				return true;
		}
		trieToConsider.reachedEnd = true;
		return false;
	}

	public void run(String args[]) throws Exception
	{

		Scanner scanner = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out, true);
			

		int numTestCases = scanner.nextInt();

		int copyNumTestCases = numTestCases;

		while(copyNumTestCases -- > 0 )
		{
			int numWords = scanner.nextInt();
			int copyNumWords = numWords;
			strs = new String [numWords];
			boolean flagResult = false;
			tries = new Trie();
			while(copyNumWords -- > 0)
			{	

				strs[numWords - copyNumWords -1] = scanner.next();
				if(flagResult)
					continue;
				flagResult = insertTrie(strs[numWords-copyNumWords -1]);

			} 
			if(flagResult == true)
				out.println("NO");
			else
				out.println("YES");
		}
	}
}