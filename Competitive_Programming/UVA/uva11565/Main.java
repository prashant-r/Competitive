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
			public void run(String args[]) throws Exception
			{
				Scanner scanner = new Scanner(System.in);
				out = new PrintWriter(System.out, true);
				while(scanner.hasNextLine())
				{
					int numtest = scanner.nextInt();
					scanner.nextLine();
					int A,B,C;
					while(numtest-- > 0 )
					{
						Scanner sc = new Scanner(scanner.nextLine());
						
						while(sc.hasNext())
						{
							A = sc.nextInt();
							B = sc.nextInt();
							C = sc.nextInt();
							out.println(getResult(A,B,C));
						}
					}	
				}
			}

			public String getResult(int A, int B, int C)
			{
				
			int x, y, z;
			for (x = -100; x <= 100; x++)
			{
			 for (y = -100; y <= 100; y++)
			 {
			  for (z = -100; z <= 100; z++)
				{	
				 if (y != x && z != x && z != y && x + y + z == A && x * y * z == B && x * x + y * y + z * z == C) 
				 {
					 return x +" " + y + " "+ z; 
				 }
			    }
			 }
			}
				return "No solution.";
			}

		}