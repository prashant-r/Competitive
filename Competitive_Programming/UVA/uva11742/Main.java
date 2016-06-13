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
	
	class Constraint
	{
		int a, b, c;
		public Constraint(int a, int b, int c)
		{
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}
	int n,m;
	List<Constraint> constraints = new ArrayList<Constraint>();
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while(scanner.hasNextLine())
		{
			constraints = new ArrayList<Constraint>();
			n= scanner.nextInt();
			m= scanner.nextInt();
			if(scanner.hasNextLine())
				scanner.nextLine();
			if(n == 0 && m == 0)
				break;
			if(m > 0)
			{
				while(m-- > 0)
				{
					constraints.add(new Constraint(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
				}
				if(scanner.hasNextLine())
					scanner.nextLine();
			}	

			
			int count = 0;
			List<Set<Integer>> allPermutes = getAllPermutations(0, new LinkedHashSet<Integer>());
			for(Set<Integer> permutation: allPermutes)
			{
				if(permutationIslegal(permutation))
				{
					++count;
				}
			}
			out.println(count);
		}
	}

	public boolean permutationIslegal(Set<Integer> set)
	{
		boolean allConstraintsSat = true;
		List<Integer> list = new ArrayList<Integer>(set);
		for(Constraint constraint: constraints)
		{
			if(constraint.c < 0)
				if(Math.abs(list.indexOf(constraint.a) - list.indexOf(constraint.b)) >= -constraint.c)
				{
					allConstraintsSat = true;
				}
				else
					allConstraintsSat = false;
			else
				if(Math.abs(list.indexOf(constraint.a) - list.indexOf(constraint.b)) <= constraint.c)
				{
					allConstraintsSat = true;
				}
				else
					allConstraintsSat = false;
			if(!allConstraintsSat)
				break;
		}
		return allConstraintsSat;
	}

	public List<Set<Integer>> getAllPermutations(int x, Set<Integer> set)
	{
		List<Set<Integer>> lists= new ArrayList<Set<Integer>>();
		if(x == n)
		{
			Set<Integer> newSet = new LinkedHashSet<Integer>(set);
			lists.add(newSet);
			return lists;
		} 
		for(int a = 0 ; a < n ;a++)
		{	
			if(!set.contains(a))
			{
				set.add(a);
				lists.addAll(getAllPermutations(x+1, set));
				set.remove(a);
			}
		}
		return lists;
	}
}