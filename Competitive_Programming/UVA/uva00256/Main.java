import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 

 // Accepted on UVA
 
public class Main {
    public static void main (String args[]) throws Exception
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String input;
        while((input=br.readLine())!=null)
        {
            int lowerbound=0;
            int upperbound=0;
            int divider=10;
            switch (Integer.parseInt(input)) {
                case 2:
                {
                    lowerbound=00;
                    upperbound=99;
                    divider=10;
                    break;
                }
                case 4:
                {
                    lowerbound=0000;
                    upperbound=9999;
                    divider=100;
                    break;
                }
                case 6:
                {
                    lowerbound=000000;
                    upperbound=999999;
                    divider=1000;
                    break;
                }
                case 8:
                {
                    lowerbound=00000000;
                    upperbound=99999999;
                    divider=10000;
                    break;
                }
            }
            for (int i = lowerbound; i <= upperbound; i++) {
                int a=i/divider;
                int b=i-a*divider;
                if((a+b)*(a+b)==i)
                {
                    System.out.format("%0"+Integer.parseInt(input)+"d\n",i);
                }
            }
        }
    }
}