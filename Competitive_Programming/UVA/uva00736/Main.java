import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.*;


class Main
{
	public Main()
	{


	}
	public static void main(String args[]) throws Exception
	{
		new Main().run(args);
	}
	boolean debug;
	int x;
	int y;
	char matrix[][];
	boolean visiteds[][];
	int rowLength;
	int colLength;
	List<Pair> needToCheck;
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		needToCheck = new ArrayList<Pair>();
		needToCheck.add(new Pair(-1,0,"N"));
		needToCheck.add(new Pair(-1,1, "NE"));
		needToCheck.add(new Pair(0,1, "E"));
		needToCheck.add(new Pair(1,1, "SE"));
		needToCheck.add(new Pair(1,0, "S"));
		needToCheck.add(new Pair(1,-1, "SW"));
		needToCheck.add(new Pair(0,-1,"W"));
		needToCheck.add(new Pair(-1,-1, "NW"));
		matrix = new char[10000][10000];
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		int testCases = scanner.nextInt();
		int copyTestCases = testCases;
		scanner.nextLine();
		scanner.nextLine();
		int numberRows = scanner.nextInt();
		scanner.nextLine();
		
		while(copyTestCases-- >0)
		{
			int rowNum = 0;	
			matrix = new char[10000][10000];
			while(numberRows-- > 0)
			{
				String row = scanner.nextLine();
				Scanner sc = new Scanner(row);
				String parsed = sc.nextLine();
				for(int a = 0; a< parsed.length(); a++)
				{
					matrix[rowNum][a] = parsed.charAt(a);
				}
				rowLength = parsed.length();
				rowNum++;
			}
			colLength = rowNum;

			while(scanner.hasNextLine())
			{
				String query = scanner.nextLine();
				query = query.trim();
				if(isNumber(query))
				{
					numberRows = Integer.parseInt(query);
					break;
				}
				if(query.isEmpty())
				{
					continue;
				}
				runQuery(query);
			}
			out.println();
		}	
	}


	public boolean isNumber(String str)
	{
  		return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}

	public void runQuery(String query)
	{
		out.println();
		out.println(query);
		boolean t = false;
		System.out.println("not found");
		return;
		// for (int i = 0; i < rowLength; i++) {
  //              for (int j = 0; j < colLength; j++) {
  //             		 	x =i;
  //              			y =j;
                        
  //              			for(Pair check: needToCheck)
  //                       {
  //                       	if (check(check, query))
  //                       	{
  //                       		out.printf("(%d,%d) - %s%n", x+1, y+1, check.direction);
  //                              	t = true;
  //                       	}
  //                       }
  //                   }
                   
  //       }
  //           if (!t) {
  //               System.out.println("not found");
  //           }
		// return;
	}
	class Pair
	{

		int x,y;
		String direction;
		public Pair(int x, int y, String direction)
		{
			this.x = x;
			this.direction = direction;
			this.y = y;
		}

	}

	boolean check(Pair direction, String query) {

		int dx = direction.x;
		int dy = direction.y;
		String str = query;
        int nn = str.length();
        int xx=0, yy=0;
        int i = 0;
        int lastChecked = 0;
        while(i < nn)
        {
            xx = lastChecked*dx + x;
            yy = lastChecked*dy + y;

    
            if (xx < rowLength && xx >= 0 && yy < colLength && yy >= 0) {
               	if( matrix[xx][yy] == ' ' && i!=0)
                {
                	lastChecked++;
                }
                else if (matrix[xx][yy] != str.charAt(i)) {
                    return false;
                }
                else
                {
                	i++;
                	lastChecked++;
                }
            } else
                return false;
        }
        return true;
    }

}