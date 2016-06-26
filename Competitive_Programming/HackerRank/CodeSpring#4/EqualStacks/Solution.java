import java.io.*;
import java.math.*;
import java.util.*;

public class Solution
{
	public Solution()
	{

	}
	static int memo[][][];
	static int first[];
	static int second[];
	static int third[];
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        int n1 = in.nextInt();
        int n2 = in.nextInt();
        int n3 = in.nextInt();
        int h1[] = new int[n1];
        first = new int[n1];
        memo = new int[n1+1][n2+1][n3+1];
        for(int a = 0; a <= n1; a ++)
        	for(int b = 0; b <=n2; b++)
        		for(int c = 0; c <=n3; c++)
        			memo[a][b][c] = -1;
        for(int h1_i=0; h1_i < n1; h1_i++){
            h1[h1_i] = in.nextInt();
        }
        int h2[] = new int[n2];
        second =  new int[n2];
        for(int h2_i=0; h2_i < n2; h2_i++){
            h2[h2_i] = in.nextInt();
        }
        int h3[] = new int[n3];
        third = new int[n3];
        for(int h3_i=0; h3_i < n3; h3_i++){
            h3[h3_i] = in.nextInt();
        }

        for(int a = n1-1; a >=0; a--)
        {
        	if(a == n1-1)
        		first[0] = h1[a];
        	else
        		first[n1-1 -a] = first[n1-1-a-1] + h1[a];
        }
        for(int a = n2-1; a >=0; a--)
        {
        	if(a == n2-1)
        		second[0] = h2[a];
        	else
        		second[n2-1 -a] = second[n2-1-a-1] + h2[a];
        }
        for(int a = n3-1; a >=0; a--)
        {
        	if(a == n3-1)
        		third[0] = h3[a];
        	else
        		third[n3-1 -a] = third[n3-1-a-1] + h3[a];
        }
        System.out.println(findMax(n1-1, n2-1, n3-1));

    }


	public static int findMax(int fh, int sh, int th)
	{
		if(fh <0 || sh < 0 || th< 0)
		{
			return 0;
		}
		if(memo[fh][sh][th] != -1)
		{
			return memo[fh][sh][th];
		}
		if(first[fh] == second[sh] && (second[sh] == third[th]))
		{
			memo[fh][sh][th] = first[fh];
			return first[fh];
		}
		memo[fh][sh][th] =  Math.max(Math.max(findMax(fh-1, sh, th), findMax(fh,sh-1,th)),findMax(fh,sh,th-1));
		return memo[fh][sh][th];
	}
}