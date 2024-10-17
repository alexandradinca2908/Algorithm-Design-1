import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

public class Oferta {

	public static final class Task {
		public static final String INPUT_FILE = "oferta.in";
		public static final String OUTPUT_FILE = "oferta.out";

		int n;
		int k;
		int[] price;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Reader reader = new FileReader(INPUT_FILE);
				MyScanner sc = new MyScanner(reader);

				n = sc.nextInt();
				k = sc.nextInt();
				price = new int[n + 1];

				for (int i = 1; i <= n; i++) {
					price[i] = sc.nextInt();
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(Double result) {
			try {
				Writer writer = new FileWriter(OUTPUT_FILE);
				PrintWriter pw = new PrintWriter(writer);

				pw.printf("%.1f\n", result);

				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		double min(double a, double b) {
			if (a < b) {
				return a;
			} else {
				return b;
			}
		}
		private double getResult() {
			readInput();

			double[][] dp = new double[n + 1][n + 1];

			//  Main diagonal
			for (int i = 1; i <= n; i++) {
				dp[i][i] = price[i];
			}

			//  Second upper diagonal
			for (int i = 1; i <= n - 1; i++) {
				//  Min value from 2 adjacent prices
				double min = min(dp[i][i], dp[i + 1][i + 1]);
				dp[i][i + 1] = dp[i][i] + dp[i + 1][i + 1] - min / 2;
			}

			//  Third upper diagonal
			for (int i = 1; i <= n - 2; i++) {
				//  Min value from 3 adjacent prices
				double min = min(min(dp[i][i], dp[i + 1][i + 1]),
						dp[i + 2][i + 2]);

				double firstOpt = dp[i][i + 1] + dp[i + 2][i + 2];
				double secondOpt = dp[i][i] + dp[i + 1][i + 1]
						+ dp[i + 2][i + 2] - min;

				dp[i][i + 2] = min(firstOpt, secondOpt);
			}

			//  Rest of diagonals, starting from 4th
			//  They are just combinations of minimum values
			for (int k = 3; k + 1 <= n; k++) {
				for (int row = 1; row + k <= n; row++) {
					// 	Parse is done with dp[row][row + k]
					//  Calculate min price for 1 + k t elements
					int firstHalf = row;
					int secondHalf = row + 1;
					double min = Double.MAX_VALUE;
					while (firstHalf < row + k
							&& secondHalf <= row + k) {
						if (dp[row][firstHalf] + dp[secondHalf][row + k] < min) {
							min = dp[row][firstHalf] + dp[secondHalf][row + k];
						}
						firstHalf++;
						secondHalf++;
					}
					dp[row][row + k] = min;
				}
			}

			return dp[1][n];
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
