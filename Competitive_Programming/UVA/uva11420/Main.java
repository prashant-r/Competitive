import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.*;

// Accepted on UVA :)

class Main {
	public Main() {


	}

	// 0 signifies ending at L
	// 1 signifier ending at U
	public static void main(String args[]) throws Exception {
		new Main().run(args);
	}
	long arr[][][];
	PrintWriter out;
	public void run(String args[]) throws Exception {
		Scanner scanner = new Scanner(System.in);
		out = new PrintWriter(System.out, true);
		while (scanner.hasNextLine()) {
			int n = scanner.nextInt();
			int s = scanner.nextInt();
			if (n == -1 && s == -1)
				break;
			arr = new long[66][66][2];
			for (int a = 0; a < 66; a++) {
				for (int b = 0 ; b < 66; b++) {
					for (int c = 0 ; c < 2; c++) {
						arr[a][b][c] = -1;
					}
				}
			}
			if (scanner.hasNextLine())
				scanner.nextLine();
			out.println(dp(n, s, 1));
		}
	}

	long dp(int n, int s, int x) {
		if (n == 1) {
			if (s == 0 && x == 1)
				return 1;
			if (s == 0 && x == 0)
				return 2;
			if (s == 1 && x == 1)
				return 1;
			return 0;
		}
		if (s < 0)
			return 0;
		if (n < s)
			return 0;
		if (n == s && x == 0)
			return 0;
		if (arr[n][s][x] != -1)
			return arr[n][s][x];
		if (x == 1)
			arr[n][s][x] =  dp(n - 1, s - 1, 1) + dp(n - 1, s, 0);
		else
			arr[n][s][x] =  dp(n - 1, s, 1) + dp(n - 1, s, 0);
		return arr[n][s][x];
	}

}