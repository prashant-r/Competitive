import java.util.*;
import java.io.*;

// Accepted on UVA :)
class Main
{
	public Main()
	{
  
	}
	public void run(String args[]) throws Exception 
	{
		Scanner scanner = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out, true);
		int numTestCases = scanner.nextInt();
		scanner.nextLine();
		int copyNumTestCases = numTestCases;
		while(copyNumTestCases-- > 0)
		{
			out.println(distinguish(scanner.nextLine()));
		}
		out.close();
		scanner.close();
		return;
	}


	public String distinguish(String given)
	{
		T = given + given;
		P = reverseString(given);
		N = T.length();
		M = P.length();	
		b = new int[M + 1];
		kmpPreprocess();
		int index = kmpProcess();
		if(index == P.length() || index == 0)
		{
			return "palindrome";
		}
		else if(index == -1)
		{
			return "simple";
		}
		else
		{
			return "alindrome";
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
	static String P, T;
	static int M, N;
	static int[] b;
	
	static void kmpPreprocess() {
		int i = 0, j = -1;
		b[0] = -1;
		
		while (i < M) {
			while (j >= 0 && P.charAt(i) != P.charAt(j)) j = b[j];
			++i; ++j;
			b[i] = j;
		}
	}
	
	static int kmpProcess() {
		int i = 0, j = 0;
		int flag = -1;
		while (i < N) {
			while (j >= 0 && T.charAt(i) != P.charAt(j))
			{

				j = b[j];
			}
			++i; ++j;
			if (j == M) {
				flag = i-j;
				if (flag > 0)
					return flag;
				j = b[j];
			}
		}
		return flag;
	}



	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
		System.exit(0);

	}
}