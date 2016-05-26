import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

// Not tested on UVA
class Main
{
	
	public Main()
	{

	}

	String firstNames[];
	String lastNames[];
	String numbers [];
	String dialed [];
	String ned;
	String converted [];
	public void run(String args[]) throws Exception
	{
		Scanner scanner = new Scanner(new FileInputStream(args[0]));
		firstNames   = new String[1024];
		lastNames = new String [1024];
		numbers = new String[1024];
		dialed = new String[1024];
		converted = new String[1024];
		ned = "22233344455566677778889999";
		int n = 0;
		int c = 0;
		while(scanner.hasNext())
		{
			String firstName = scanner.next();
			if(firstName.charAt(0) > '0'  && firstName.charAt(0) < '9')
			{
				while(true)
				{
				c++;
				dialed [c] = firstName;
				printResults(dialed[c], n);
				if(!scanner.hasNext())
					break;
				firstName = scanner.next();
				}
				break;
			}
			String lastName = scanner.next();
			String number = scanner.next();
			n++;
			firstNames[n] = firstName;
			lastNames[n] = lastName.toLowerCase();
			numbers[n] = number;
			int index1 = firstNames[n].toLowerCase().charAt(0) - 'a';
			converted[n] = "" + ned.charAt(index1);
			for(int a =0; a < lastName.length(); a++)
			{
				converted[n]  = converted[n] + "" + ned.charAt(lastNames[n].charAt(a) - 'a');
			}

		}


	}

	public void printResults(String a, int count)
	{
		int result = 0;
		PrintWriter out = new PrintWriter(System.out, true);
		for(int g = 1; g <=count; g++)
		{
			//System.out.println( converted[g] + " != " + a);
			if(numbers[g].equalsIgnoreCase(a))
			{
				out.println(numbers[g]);
				return;
			}
		}
		String output = "";
		boolean everFound = false;
		for(int h = 1; h <=count ; h++)
		{
			boolean wasFound = true;
			
			if(a.length() > converted[h].length())
			{
				wasFound = false;
				continue;
			}
			for(int x =0; x < a.length() ; x++)
			{

				if(x < converted[h].length())
				{
				if(converted[h].charAt(x) != a.charAt(x))
				{
					wasFound = false;
					break;
				}
				}

			}
			if(wasFound)
			{
				everFound = true;
				output = output + numbers[h] + " ";
			}
		}

		if(!everFound)
			out.println(result);
		else
			out.println(output.trim());
	}


	public static void main(String args[]) throws Exception
	{
		new Main().run(args);																														
	}
}