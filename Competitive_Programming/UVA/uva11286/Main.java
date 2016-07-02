import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			int N = Integer.parseInt(in.readLine());
			if (N == 0)
				break;
			
			Map<String, Integer> map = new TreeMap<String, Integer>();
			for (int i = 0; i < N; ++i) {
				String[] parts = in.readLine().split("[ ]+");
				Arrays.sort(parts);
				
				String s = "";
				for (int j = 0; j < parts.length; ++j)
					s += parts[j];
				
				if (map.containsKey(s))
					map.put(s, map.get(s) + 1);
				else
					map.put(s, 1);
			}
			int max = 0;
			for (int x : map.values())
				max = Math.max(max, x);
			
			int cnt = 0;
			for (int x : map.values())
				if (x == max)
					cnt += x;
			System.out.println(cnt);
		}
		
		in.close();
		System.exit(0);
	}
}