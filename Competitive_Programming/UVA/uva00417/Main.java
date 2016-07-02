import java.io.*;
import java.util.*;
import java.math.*;

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
	List<String> list=new ArrayList<>();
	PrintWriter out;
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out,true);
		generateAllWords("", 0);
		Collections.sort(list, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                if(o1.length()>o2.length()){
                    return 1;
                }
                else if(o1.length() < o2.length())
                {
                	return -1;
                }
                else{
                    return o1.compareTo(o2);
                }
            }
        });
		while(scanner.hasNextLine())
		{
			String s = "";
			if(scanner.hasNextLine())
				s = scanner.nextLine();
			if(!s.isEmpty())
			{
				s = s.toUpperCase();
				int index = list.indexOf(s);
				out.println( index == -1 ? 0 : index);
			}
		}
	}

	public void generateAllWords(String generated, int lastChar)
	{
		if(generated.length() <5)
			list.add(generated);
		if(generated.length() == 5)
		{
			list.add(generated);
			return;
		}
		for(int a= lastChar; a<26; a++)
		{
			generateAllWords(generated + (char) (a+65), a+1);
		}
	}
}