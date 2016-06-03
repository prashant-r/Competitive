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

	public static void main(String args[]) throws Exception 
	{
		 new Main().run(args);
	}

	char matrix[][];
	int lengthSide;
	int x;
	int y;
	String query;
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);

		lengthSide = scanner.nextInt();
		scanner.nextLine();
		while(true)
		{
			
			if(lengthSide == 0)
				break;
			matrix = new char[lengthSide][lengthSide];
			for(int a= 0; a< lengthSide ;a++)
			{
				String row = scanner.nextLine();
				for(int b = 0; b < lengthSide; b++)
				{
					matrix[a][b] = row.charAt(b);
				}
			}
			for(;;)
			{
				query = scanner.nextLine();
				if(query.matches("^-?\\d+$"))
				{
					lengthSide = Integer.parseInt(query);
					break;
				}
				runQuery();
			}
		}

	}

	class Coordinate
	{
		int x;
		int y;
		public Coordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}

	public void runQuery()
	{
		int n = lengthSide;
		boolean t = false;

		for (int i = 0; i < n; i++) {
               for (int j = 0; j < n; j++) {
              		 	x =i;
               			y = j;
                        if (check(1,0) || check(-1, 0)
                            || check(0,1) || check(0, -1)
                            || check(1,1) || check(1,-1)
                            || check(-1, -1) || check(-1, 1)) {
                            t = true;
                            break;
                        }
                    }
                    if (t) {
                        break;
                    }
                }
            if (!t) {
                out.println("Not found");
            }
		return;
	}


	boolean check(int dx, int dy) {
		String str = query;
		int n = lengthSide;
        int nn = str.length();
        int xx=0, yy=0;
        for (int i = 0; i < nn; i++) {
            xx = i*dx + x;
            yy = i*dy + y;

            if (xx < n && xx >= 0 && yy < n && yy >= 0) {
                if (matrix[xx][yy] != str.charAt(i)) {
                    return false;
                }
            } else
                return false;
        }
        out.printf("%d,%d %d,%d%n", x+1, y+1, xx+1, yy+1);

        return true;
    }


}