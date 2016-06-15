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
	static int total;
	static int zero;

	PrintWriter out;
	public void run(String args[]) throws Exception {
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while (scanner.hasNextLine()) {
			total = scanner.nextInt();
			zero = scanner.nextInt();
			if (total == 0 && zero == 0 )
				break;
			if (scanner.hasNextLine())
				scanner.nextLine();
			out.println(getAnswer());
		}
	}
	public static int getAnswer() {
		int i = 1;
		double v = total * 1.0 / i;
		double best = 0;
		int result = 0;

		while (v > zero) {
			double tmp = 0.3 * Math.sqrt(v - zero);

			if (Math.abs(tmp * i - best) < 0.00000001) {
				result = 0;
				break;
			}

			if (tmp * i > best) {
				best = tmp * i;
				result = i;
			}

			i++;
			v = total * 1.0 / i;
		}

		return result;
	}
}