import java.util.*;
import java.io.*;
import java.lang.*;
import java.math.*;

// Accepted on UVA :)

class Main
{
	public Main()
	{

	}

	int coinHeights[] ;
	int tableHeights[];

	public int gcd(int a, int b) {
   		if (b==0) return a;
   	return gcd(b,a%b);
	}


	public int lcm(int a, int b)
	{

		return a*b/(gcd(a,b));
	}

	class Pair
	{
		int smallest;
		int largest;
		public Pair(int smallest, int largest)
		{
			this.smallest = smallest;
			this.largest = largest;
		}
	}

	class Quad
	{
		int leg1coin;
		int leg2coin;
		int leg3coin;
		int leg4coin;

		public void set(int index, int a)
		{
			if(index == 0)
			{
				leg1coin = a;
			}
			else if(index == 1)
			{
				leg2coin = a;

			}
			else if(index == 2)
			{
				leg3coin =a;

			}
			else if(index == 3)
			{
				leg4coin = a;
			}
		}
		public boolean contains(int a)
		{
			if( a== leg1coin || a == leg2coin || a == leg3coin || a == leg4coin)
				return true;
			else
				return false;

		}

		public String toString()
		{
			return leg1coin + " " + leg2coin + " " + leg3coin + " " + leg4coin;
		}

		void copy(Quad quad)
		{
			this.leg1coin = quad.leg1coin;
			this.leg2coin = quad.leg2coin;
			this.leg3coin = quad.leg3coin;
			this.leg4coin = quad.leg4coin;
		}
	}
	public void run(String args[]) throws Exception
	{

		Scanner scanner = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out, true);
		while(true)
		{
			int n = scanner.nextInt();
			int t = scanner.nextInt();
			quads = new ArrayList<Quad>();
			if(n == 0 && t == 0)
				break;
			coinHeights = new int[n+1];
			tableHeights = new int[t+1];
			int copyn = n;
			int copyt = t;
			while(copyn-- > 0)
			{
				coinHeights[n-copyn] = scanner.nextInt();
			}
			while(copyt -- > 0 )
			{
				tableHeights[t-copyt] = scanner.nextInt();
			}
			List<Pair> results = constructResult();
			for(Pair result: results)
				System.out.println(result.smallest + " " + result.largest);
		}
	}

	public List<Pair> constructResult()
	{
		
		int n = coinHeights.length;
		int t = tableHeights.length;
		List<Pair> answers = new ArrayList<Pair>();
		// find all combinations of coins
		findAllCombindations(new Quad(), 0, 0);
		List<Quad> combinations = quads;
		int min =Integer.MAX_VALUE;;
		int max = 0;
		for(int a =1 ; a< t; a++)
		{
			min =Integer.MAX_VALUE;;
			max = 0;
			Pair answer = new Pair(0,0);

			int tableHeight = tableHeights[a];
			for(Quad combn: combinations)
			{
				int len = lcm(lcm(combn.leg1coin, combn.leg2coin),
                                              lcm(combn.leg3coin, combn.leg4coin));
                
                max = Math.max(max, (tableHeight / len) * len);
                min = Math.min(min,(tableHeight / len + ((tableHeight % len != 0) ? 1 : 0))* len);
			}
			answer.smallest = max;
			answer.largest = min;
			answers.add(answer);

		}

		return answers;

	}
	List<Quad> quads = new ArrayList<Quad>();
	public void findAllCombindations(Quad soFar, int index, int a)
	{
		if(index == 4)
		{
			quads.add(soFar);
			return;
		}
		
		if(a == coinHeights.length-1)
			return;
		Quad toUse = new Quad();
		toUse.copy(soFar);
		findAllCombindations(toUse, index, a+1);
		toUse = new Quad();
		toUse.copy(soFar);
		toUse.set(index, coinHeights[a+1]);
		findAllCombindations(toUse, index+1, a+1);
		return;
	}

	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}
}