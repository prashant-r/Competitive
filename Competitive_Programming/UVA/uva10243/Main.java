import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;

// Accepted on UVA :)

class Main
{
	
	public Main()
	{

	}
	Tree [] galTrees; 
	public Tree root; 
	class Tree
	{
		List<Tree> children;
		int id;
		public Tree(int id, List<Tree> children)
		{
			this.id = id;
			this.children = children;
		}
	}

	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out, true);


		while(true)
		{
			int numGalleries = scanner.nextInt();
				
			if(numGalleries == 0)
				break;
			galTrees = new Tree[numGalleries + 1];

			for(int a= 1; a <= numGalleries; a++)
				galTrees[a] = new Tree(a, new ArrayList<Tree> ());

			int copyNumGalleries = numGalleries;
			while(copyNumGalleries -- > 0)
			{
				int adjGalCount = scanner.nextInt();
				int copyAdjGalCount = adjGalCount;
				int runningFor = numGalleries - copyNumGalleries;
				while(copyAdjGalCount -- > 0)
				{
					int b = scanner.nextInt();
					galTrees[runningFor].children.add(galTrees[b]);

				}
			}
			Pair answer  = findMinVertexCover(galTrees[galTrees.length - 1], -1);
			out.println(Math.min(answer.selected, answer.selected_or_notselected));
		}

	}

	class Pair
	{
		int selected;
		int selected_or_notselected;

		public String toString()
		{
			return " selected " +  selected + " not selected " + selected_or_notselected;
		}
	}

	public Pair findMinVertexCover(Tree root, int parentId)
	{
		int numChildren  = 0;

		//System.out.println(" root " + root.id );

		List<Pair> childrenResults = new ArrayList<Pair>();
		
		for(Tree child: root.children)
		{
			if(child.id != parentId)
			{
				numChildren ++;
				childrenResults.add(findMinVertexCover(child, root.id));
			}
		}

		// if leaf node
		if(numChildren == 0)
		{
			if(parentId == -1)
			{
				Pair pair = new Pair();
				pair.selected = 1;
				pair.selected_or_notselected = 1;
				return pair;
			}
			else
			{
				Pair pair = new Pair();
				pair.selected = 1;
				pair.selected_or_notselected =0;
				return pair;
			}
		}

		// internal node
		else
		{
			int cost_of_selecting_children = 0;
			int cost_of_selecting_or_not_selecting_children = 0;

			for(Pair childResult : childrenResults)
			{
				cost_of_selecting_children += childResult.selected;
				cost_of_selecting_or_not_selecting_children+=childResult.selected_or_notselected;
			}

			int selecting_me_cost = 1+cost_of_selecting_or_not_selecting_children;
			int not_selecting_me_cost = cost_of_selecting_children;

			Pair myPair = new Pair();
			myPair.selected_or_notselected = Math.min(selecting_me_cost, not_selecting_me_cost);
			myPair.selected = selecting_me_cost;
			return myPair;

		}
	}

	public static void main(String args[]) throws Exception
	{

		new Main().run(args);
	}
}