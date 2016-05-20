import java.util.*;
import java.io.*;
import java.text.*;
import java.math.*;


// Accepted on UVA judge :)
class Main
{
  public double [] memo;
  public final double INF = 20000000;
  public final double NEG_INF = -1;
  public double distanceMatrix [][];
  public Main()
  {

  }
  public static void main(String args[]) throws Exception
  {

    new Main().run(args);
  }
  public void run(String args[]) throws Exception
  {
    PrintWriter out = new PrintWriter(System.out, true);
    memo = new double[1<<20];
    Scanner sc = new Scanner(System.in);
    int counter =1;
    while(true)
    {
      int count;
      if((count = sc.nextInt()) == 0)
      break;
      count = count *2;
      int savecount= count;
      int [] x= new int[count];
      int [] y = new int[count];
      String [] names = new String[count];
      Arrays.fill(memo, NEG_INF);
      distanceMatrix = new double [count][count];
      while(count-- >0)
      {
        String name = sc.next();
        int distx = sc.nextInt();
        int disty = sc.nextInt();
        x[count] = distx;
        y[count] = disty;
        names[count] = name;
      }
      count = savecount;
      for(int a = 0; a < count; a++ )
      {
        for(int b = 0; b < count ; b++)
        {
          if(b != a)
            distanceMatrix[a][b] = distanceMatrix[b][a] = distance(x[a]-x[b], y[a]-y[b]);
        }
      }
      DecimalFormat four = new DecimalFormat("#0.00");
      out.println("Case " + counter + ": "+ four.format(solve(x,y,savecount,0, (int)Math.pow(2, savecount) -1)));
      counter++;
    }


  }


  public double solve(int [] x, int y [], int counter, int mask , int match)
  {
    int a;
    int b;
    if( mask == (match))
    {
      return 0.0;
    }
    if(memo[mask]>=0 && memo[mask]!= INF)
    {
      return memo[mask];
    }
    int check = 1;
    double distance = INF;
    for(a =0; a<counter ;a ++ )
    {
      if(((check << a ) & mask) == 0)
      {
        mask = mask | (check << a);
        for(b = 0; b<counter ; b ++ )
        {
          if(((check << b ) & mask) == 0 && b> a)
          {
            double firstquant = solve(x,y, counter, mask | (check <<b), match) + distanceMatrix[a][b];
            double secondquant = distance;
            distance = Math.min(firstquant,secondquant);
          }
        }
        break;
      }
    }
    memo[mask] = distance;
    return distance;
  }


  static double distance(int a, int b)
  {
    return Math.pow((Math.pow(a,2) + Math.pow(b,2)),0.5);
  }
}
