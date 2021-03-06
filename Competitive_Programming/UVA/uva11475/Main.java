import java.io.*;

// Accepted on UVA :)

public class Main {
	static String P,T;
	static int M, N;
	static int[] b;
	
	static void kmpPreprocess() {
		int i = 0, j = -1;
		b[i] = j;
		
		while (i < M) {
			while (j >= 0 && P.charAt(i) != P.charAt(j)) j = b[j];
			++i; ++j;
			b[i] = j;
		}
	}
	
	static int kmpSearch() {
		int i = 0, j = 0;
		
		while (i < N) {
			while (j >= 0 && T.charAt(i) != P.charAt(j)) j = b[j];
			++i; ++j;
		}
		return j;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		while ((T = in.readLine()) != null) {
			P = new StringBuffer(T).reverse().toString();
			M = N = P.length();
			
			b = new int[M + 1];
			
			kmpPreprocess();
			int ind = kmpSearch();
			System.out.println(T + P.substring(ind));
		}
		
		in.close();
		System.exit(0);
	}
}