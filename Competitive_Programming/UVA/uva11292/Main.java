import java.util.*;
import java.io.*;
import java.math.*;

// Accepted on UVA judge :)

class Main
{
  public Main()
  {

  }
  public void run(String args[]) throws Exception
  {
    Scanner sc = new Scanner(System.in());
    PrintWriter out = new PrintWriter(System.out, true);
    while(true)
    {
      int numheads = sc.nextInt();
      int numsol = sc.nextInt();

      int numheadscopy = numheads;
      int numsolcopy = numsol;

      if(numsol == 0 && numheads == 0)
      {
        return;
      }
      List<Integer> solhs = new ArrayList<Integer>();
      int headhs [] = new int[numheads];
      while(numheads-- > 0)
      {
        headhs[numheads] = sc.nextInt();
      }
      while(numsol-- >0)
      {
        solhs.add(sc.nextInt());
      }
      numheads = numheadscopy;
      numsol = numsolcopy;
      Collections.sort(solhs);
      if(numsol < numheads)
      {
        out.println("Loowater is doomed!");
        continue;
      }
      int headsat = 0;
      int total = 0;
     //System.out.println(" What's the original solhs" + solhs);
      for(int a = 0; a < numheads; a++ )
      {
      //  System.out.println(" Head height " + headhs[a]);


        int index;
        if(solhs.size() == 1)
        {
          if(solhs.get(0) >=headhs[a]){
          //System.out.println("Selected " + solhs.get(0) + " at index "+ 0 + " new solhs " + solhs);
          total = total + solhs.get(0);
          headsat++;
          solhs.remove(0);
        }
        }
        else if((index = binarySearch(headhs[a], solhs)) != -1)
        {
        //  System.out.println(" Returned index "+ index);
          boolean found = false;
          int lastIndex = index;

          if(solhs.get(index) >=headhs[a])
          {
            found = true;
            lastIndex = index;
          }
          if(solhs.get(index-1) >=headhs[a])
          {
            found = true;
            lastIndex = index-1;
          }
          if(found)
          {
        //    System.out.println("Selected " + solhs.get(lastIndex) + " at index "+ lastIndex);
            total = total + solhs.get(lastIndex);
            headsat++;
            solhs.remove(lastIndex);
          //  System.out.println(" new solhs " + solhs);
          }
          else
          {
          //  out.println(" Not found for " + headhs[a]);
          }
        }

      }
      if(headsat == numheads)
        out.println(total);
      else
        out.println("Loowater is doomed!");
    }

  }

  public static void main(String args[]) throws Exception
  {
    new Main().run(args);
  }

  public int binarySearch(int item, List<Integer>solhs)
  {
    int a = solhs.size()-1;
    if(a == 0)
      return -1;
    int low = 0;
    int high = a;
    int mid  = -1;
    while(low < high)
    {
      mid = (low+ high)/2;
      if( low + 1 == high)
      {
        break;
      }
      if(solhs.get(mid) > item)
      high = mid;
      else
      low = mid;

    }
    return high;
  }
}
