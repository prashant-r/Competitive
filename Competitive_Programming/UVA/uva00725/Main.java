import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
// Accepted on UVA :)

public class Main
{
    public static void main(String[] args) throws IOException 
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb=new StringBuilder("");
        String input;
        int t=0;
        while((input=br.readLine())!=null)
        {   
            int N=Integer.parseInt(input);
            if(N==0)
                break;
            if(t!=0)
            {
                sb.append("\n");
            }
            t=1;
            boolean flag=false;
            for (long i =1234; i <98765; i++)
            {
                if(isdiffdigits(i+""))
                {
                    long a=N*i;
                    if(isdiffdigits(a+"") && isdiffdigits(i+"",a+""))
                    {
                        flag=true;
                        if((i+"").length()<5)
                            sb.append(a+" / 0"+i+" = "+N+"\n");
                        else
                            sb.append(a+" / "+i+" = "+N+"\n");
                    }
                }
            }
            if(!flag)
                sb.append("There are no solutions for "+N+".\n");
             
        }
        System.out.print(sb);
    }
    public static boolean isdiffdigits(String N)
    {
        if(N.length()<5)
            N=0+N;
        for (int i = 0; i < N.length()-1; i++)
        {
            for (int j = i+1; j < N.length(); j++)
            {
                if(N.charAt(i)==N.charAt(j))
                    return false;
            }
        }
        return true;
    }
    public static boolean isdiffdigits(String N1,String N2)
    {
        if(N1.length()<5)
            N1=0+N1;
        if(N2.length()<5)
            N2=0+N2;
        for (int i = 0; i < N1.length(); i++)
        {
            for (int j = 0; j < N2.length(); j++)
            {
                if(N1.charAt(i)==N2.charAt(j))
                    return false;
            }
        }
        return true;
    }
}