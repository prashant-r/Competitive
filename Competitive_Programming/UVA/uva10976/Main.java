import java.util.*;
import java.io.*;

// Accepted on UVA :)

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = in.readLine()) != null) {
			int k = Integer.parseInt(line);
			List<Integer> xd = new ArrayList<Integer>();
			List<Integer> yd = new ArrayList<Integer>();
			// Explanation:
			// 1/k - 1/y = 1/x so (k - y) /ky  = 1/x
			// then ky must be divisible by k-y
			// Also since 1/k = 2/t assuming y = x = t then t can be atmost 2k if x or y is larger than
			// 2k then its corresponding x or y would be <2k which should be covered when iterating until 2k
			// so one of x and y will definitely be in k+1 to 2k range. <=k is impossible
			for (int y = k + 1; y <= 2 * k; ++y) {
				if (k * y % (y - k) == 0) {
					int x = k * y / (y - k);
					xd.add(x);
					yd.add(y);
				}
			}
			
			sb.append(xd.size() + "\n");
			for (int i = 0; i < xd.size(); ++i)
				sb.append(String.format("1/%d = 1/%d + 1/%d\n", k, xd.get(i), yd.get(i)));
		}
		
		System.out.print(sb);
		
		in.close();
		System.exit(0);
	}
}