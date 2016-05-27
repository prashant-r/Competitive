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
	int b[];
	int M;
	int N;
	String T;
	String P;
	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out, true);
		int numTestCases = scanner.nextInt();
		int copyNumTestCases = numTestCases;
		scanner.nextLine();
		while(copyNumTestCases-- > 0)
		{
			P = scanner.nextLine();
			N = P.length();
			T = reverseString(P);
			M = T.length();
			b = new int[M+1];
			out.println(getSecretWord());
		}
		
	}
	public String reverseString(String input){
    char[] in = input.toCharArray();
    int begin=0;
    int end=in.length-1;
    char temp;
    while(end>begin){
        temp = in[begin];
        in[begin]=in[end];
        in[end] = temp;
        end--;
        begin++;
    }
    return new String(in);
	}


	public void kmpPreProcess()
	{
		int i = 0;
		int j = -1;
		b[0]  = -1;
		while(i < M)
		{
			while(j>=0 && P.charAt(j)!=P.charAt(i))
			{
				j = b[j];
			}
			++i;
			++j;
			b[i] = j;
		}
	}

	public int kmpSearch()
	{
		int flag = 0;
		int j =0; 
		int i =0;
		int max = 0;
		while(i < M)
		{
			while(j>=0 && T.charAt(i) != P.charAt(j))
			{
				j = b[j];
			}
			++i;
			++j;
			if(j > max)
			{
				flag = j;
				max = j;
			}
			if(j == M)
			{
				j = b[j];
			}
		}

		return flag;
	}

	public String getSecretWord()
	{
		String resultSoFar = "";
		kmpPreProcess();
		int index = kmpSearch();
		if(index == 0)
			return "" + P.charAt(0);
		else
			return reverseString(P.substring(0, index));
	}

}