import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

public class Colorare {
	static class Task {
		public static final String INPUT_FILE = "colorare.in";
		public static final String OUTPUT_FILE = "colorare.out";

		int n;
		long dp;
		char last_orientation;

		public void solve() {
			writeOutput(getResult());
		}

		private void writeOutput(Long result) {
			try {

				Writer writer = new FileWriter(OUTPUT_FILE);
				PrintWriter pw = new PrintWriter(writer);

				pw.printf("%d\n", result);

				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		//  Custom pow function that can handle big inputs
		//  Necessary to control output overflow with %(1000000000 + 7)
		private long powerCalculus(long base, int exp) {
			long result = 1;
			while (exp > 0) {
				if (exp % 2 == 1) {
					result = (result * base) % (1000000000 + 7);
				}
				base = (base * base) % (1000000000 + 7);
				exp /= 2;
			}
			return result;
		}

		private long getResult() {
			try {
				Reader reader = new FileReader(INPUT_FILE);
				MyScanner sc = new MyScanner(reader);

				int repeat;
				char orientation;

				n = sc.nextInt();

				repeat = sc.nextInt();
				orientation = sc.next().charAt(0);
				last_orientation = orientation;
				n--;

				//  First case
				//  Vertical has 3 colour combinations
				//  Horizontal has 6 colour combinations
				if (orientation == 'V') {
					dp = 3;
				} else {
					dp = 6;
				}
				repeat--;

				while (n >= 0) {
					if (repeat > 0) {
						if (orientation == 'V') {
							dp = ((dp * powerCalculus(2, repeat))) % (1000000000 + 7);
						} else {
							dp = ((dp * powerCalculus(3, repeat))) % (1000000000 + 7);
						}
					}

					if (n != 0) {
						repeat = sc.nextInt();
						orientation = sc.next().charAt(0);
						n--;

						if (orientation != last_orientation) {
							repeat--;
							if (last_orientation == 'V'
									&& orientation == 'H') {
								dp = dp * 2 % (1000000000 + 7);
							}
							//  Else dp doesn't change.
							//  H + V doesn't create new combinations
							last_orientation = orientation;
						}
					} else {
						n--;
					}
				}

				return dp;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}