import java.io.*;

// Accepted on UVA :)

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(in.readLine());
		boolean first = true;
		while (T-- > 0) {
			int D = Integer.parseInt(in.readLine());
			String[] names = new String[D];
			int[] L = new int[D];
			int[] H = new int[D];
			
			for (int i = 0; i < D; ++i) {
				String[] parts = in.readLine().split("[ ]+");
				names[i] = parts[0];
				L[i] = Integer.parseInt(parts[1]);
				H[i] = Integer.parseInt(parts[2]);
			}
			if (first)
				first = false;
			else
				System.out.println();
			
			int Q = Integer.parseInt(in.readLine());
			
			for (int i = 0; i < Q; ++i) {
				int matches = 0;
				String match = "";
				int query = Integer.parseInt(in.readLine());
				
				for (int j = 0; j < D; ++j)
					if (query >= L[j] && query <= H[j]) {
						++matches;
						match = names[j];
					}
				
				if (matches == 1)
					System.out.println(match);
				else
					System.out.println("UNDETERMINED");
			}
		}
		
		in.close();
		System.exit(0);
	}
}