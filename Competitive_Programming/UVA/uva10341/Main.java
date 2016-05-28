import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;
import java.text.*;

// Accepted on UVA :)
class Main
{

public final double EPS =  1e-7;
public Main()
{

}


public void run(String args[]) throws Exception
{

	int p,q,r,s,t,u;

	Scanner input = new Scanner(System.in);
	PrintWriter out = new PrintWriter(System.out, true);
	while(input.hasNextLine())
	{
   		p = input.nextInt();
   		q = input.nextInt();
   		r = input.nextInt();
   		s = input.nextInt();
   		t = input.nextInt();
   		u = input.nextInt();
   		input.nextLine();

   		double low = 0.0f;
		double high = 1.0f;
		double mid = 0.0f;
	    if (compute(p,q,r,s,t,u,0) * compute(p,q,r,s,t,u,1) > 0){
	      out.println("No solution");
	    } else {
		while(low + EPS < high)
		{
			mid = (low+high)/2;
			double computeVal = compute(p,q,r,s,t,u,mid);
			if(computeVal<=0)
				high = mid;
			else
				low = mid;
		}
		DecimalFormat four = new DecimalFormat("#0.0000");
      	out.println(four.format(mid));
	}

	}
}
public double compute(int p,int q,int r,int s,int t,int u, double x)
{
	 return p*Math.exp(-x) + q*Math.sin(x) + r*Math.cos(x) + s*Math.tan(x) + t*x*x + u;

}

public static void main(String args[]) throws Exception 
{

	new Main().run(args);
}
}
