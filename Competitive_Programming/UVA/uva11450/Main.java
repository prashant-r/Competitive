import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.*;


// Both approaches accepted on UVA :)

class Main {

	public Main() {

	}

	public int G[][];
	public int memo[][];
	public int M;

	public void run(String args[]) throws Exception {
		int maxNoItems = 21;
		int maxItemsList = 21;
		G = new int [maxNoItems][maxItemsList];
		Scanner scanner = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out, true);
		int numTestCases = scanner.nextInt();
		while (numTestCases-- > 0) {
			int money = scanner.nextInt();
			M = money;
			int numItems = scanner.nextInt();
			int tmpA = numItems;
			while (tmpA -- > 0) {
				int numModels = scanner.nextInt();
				int tmpB = numModels;
				G[tmpA][0] = numModels;
				while (tmpB -- > 0) {
					G[tmpA][tmpB + 1] = scanner.nextInt();
				}
			}
			memo = new int[money + 1][numItems];
			for (int a = 0; a < numItems; a++) {
				for (int b = 0; b <= money; b++) {
					memo[b][a] = -1;
				}
			}

			int maxCost = bottomUp(money, numItems - 1);
			if (maxCost == -1)
				out.println("no solution");
			else
				out.println(maxCost);
		}

	}

	public int topDown(int money , int itemIndex) {
		if (money < 0) {
			return -1;
		}
		if (itemIndex == -1) {
			return M - money;
		}
		if (memo[money][itemIndex] != -1) {
			return memo[money][itemIndex];
		}
		int ans = -1;
		for (int b = 1; b <= G[itemIndex][0]; b ++ ) {
			ans = Math.max(ans, topDown(money - G[itemIndex][b], itemIndex - 1));
		}
		return memo[money][itemIndex] = ans;
	}


	public int bottomUp(int money, int numItems) {
		int answer = -1;
		for (int a = 1; a <= money ; a++) {

			for (int b = 0; b <= numItems; b++ ) {

				for (int c = 1; c <= G[b][0]; c++) {

					if (b == 0) {
						if (G[b][c] > memo[a][b] && G[b][c] <= a) {
							memo[a][b] = G[b][c];
						}

					} else {
						int max = -1;
						if (G[b][c] <= a) {
							if (memo[a - G[b][c]][b - 1] != -1)
								memo[a][b] = Math.max(memo[a][b], memo[a - G[b][c]][b - 1] + G[b][c]);
						}
					}
				}

				memo[a][b] = Math.max(memo[a][b], memo[a - 1][b]);

			}
		}

		return memo[money][numItems];
	}

	public static void main(String args[]) throws Exception {
		new Main().run(args);

	}

}