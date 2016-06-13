import java.io.*;
import java.math.*;
import java.util.*;

// Need to complete 

public class Moneymanager 
{
     public Moneymanager()
     {
      
     }
     public static void main(String args[]) throws Exception
     {
     	Scanner scanner = new Scanner(new FileInputStream(args[0]));
     	while(scanner.hasNextLine())
     	{
 			Scanner sc = new Scanner(scanner.nextLine());
 			int a []= new int[50];
 			int b []= new int[50];
 			Arrays.fill(a, -1);
 			Arrays.fill(b,-1);
 			int count = 0;
 			while(sc.hasNext())
 			{
 				a[count++] = sc.nextInt();
				
 			}    		
 			count = 0;
 			sc = new Scanner(scanner.nextLine());
 			while(sc.hasNext())
 			{
 				b[count++] = sc.nextInt();
 			}
 			int c[] = new int[count];
 			int e[] = new int[count];
 			for(int d = 0; d < count; d++)
 			{
 				c[d] = a[d];
 				e[d] = b[d];
 			}
 			int X = scanner.nextInt();
 			if(scanner.hasNextLine())
 				scanner.nextLine();
 			System.out.println(new Moneymanager().getbest(c,e,X));
     	}
     }	

     public class Pair implements Comparable<Pair>
     {
     	int experience;
     	int money;
     	Set<Integer> visited;
     	public Pair(int money, int experience)
     	{
     		this.money = money;
     		this.experience = experience;
     		visited = new HashSet<Integer>();
     	}
     	public void addExperience(int experience)
     	{
     		this.experience+=experience;
     	}

     	@Override
     	public int compareTo(Pair x)
     	{
     		if(x.money < this.money)
     			return 1;
     		else if(x.money == this.money)
     			return 0;
     		else
     			return -1;
     	}

     	@Override
     	public String toString()
     	{
     		return "EX : " +  experience + ", MO: " + money + " (" + visited + ") | "; 
     	}

     }

	 public int getbest(int[] a, int[] b, int X)
	 {
	 	return 1;
	 }
}

