import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.*;

// Accepted on UVA :)

class Main {
	public Main() {


	}
	public static void main(String args[]) throws Exception {
		new Main().run(args);
	}

	PrintWriter out;
	public void run(String args[]) throws Exception {
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while (scanner.hasNextLine()) {
			int nTestCases = scanner.nextInt();
			if (scanner.hasNextLine())
				scanner.nextLine();
			while (nTestCases -- > 0) {
				int best = -1000000000;
				int result = -1000000000;

				int studentPop = scanner.nextInt();
				if (scanner.hasNextLine())
					scanner.nextLine();
				while (studentPop -- > 0) {
					int iq = scanner.nextInt();
					if (scanner.hasNextLine())
						scanner.nextLine();
					if (best - iq > result)
						result = best - iq;
					if (iq > best)
						best = iq;
				}
				out.println(result);
			}
		}
	}
}