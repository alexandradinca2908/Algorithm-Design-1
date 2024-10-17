import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

public class Compresie {
	static class Task {
		public static final String INPUT_FILE = "compresie.in";
		public static final String OUTPUT_FILE = "compresie.out";

		int n;
		int m;
		int[] a;
		int[] b;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Reader reader = new FileReader(INPUT_FILE);
				MyScanner sc = new MyScanner(reader);

				n = sc.nextInt();
				a = new int[n];
				for (int i = 0; i < n; i++) {
					a[i] = sc.nextInt();
				}

				m = sc.nextInt();
				b = new int[m];
				for (int i = 0; i < m; i++) {
					b[i] = sc.nextInt();
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(Integer result) {
			try {

				Writer writer = new FileWriter(OUTPUT_FILE);
				PrintWriter pw = new PrintWriter(writer);

				pw.printf("%d\n", result);

				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int getResult() {
			//  Crt index of each array
			int i = 1;
			int j = 1;
			//  Final result
			int best_len = 0;
			//  Partial sums
			int sum1 = a[0];
			int sum2 = b[0];

			//  Iterate through both arrays at the same time
			//  until a partial equal sum was found
			while (i < n && j < m) {
				if (sum1 == sum2) {
					best_len++;

					sum1 = a[i];
					sum2 = b[j];

					i++;
					j++;
				} else if (sum1 < sum2) {
					sum1 += a[i];
					i++;
				} else {
					sum2 += b[j];
					j++;
				}
			}

			//  By the end of the algorithm both indexes should reach the end
			if (i != n || j != m) {
				while (i < n) {
					sum1 += a[i];
					i++;
				}
				while (j < m) {
					sum2 += b[j];
					j++;
				}
			}

			//  Last merge needs to be checked individually sometimes
			//  since our while doesn't wait for both arrays to finish parsing
			if (sum1 != sum2) {
				return -1;
			}
			return best_len + 1;
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
