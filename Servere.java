import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

public class Servere {
	static class Task {
		public static final String INPUT_FILE = "servere.in";
		public static final String OUTPUT_FILE = "servere.out";

		int n;
		int[] p;
		int[] c;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Reader reader = new FileReader(INPUT_FILE);
				MyScanner sc = new MyScanner(reader);

				n = sc.nextInt();
				p = new int[n];
				for (int i = 0; i < n; i++) {
					p[i] = sc.nextInt();
				}
				c = new int[n];
				for (int i = 0; i < n; i++) {
					c[i] = sc.nextInt();
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

		//  Calculates a server's computing power based on P - |C - delay|
		private int computingPower(int unitPower, int current, int delay) {
			return unitPower - Math.abs(current - delay);
		}

		private double computingPower(int unitPower, int current, double delay) {
			return unitPower - Math.abs(current - delay);
		}

		//  Calculates system's total power for a certain current
		private int minPowerForCurrent(int current) {
			int min = Integer.MAX_VALUE;

			for (int i = 0; i < n; i++) {
				int compPow = computingPower(p[i], c[i], current);
				if (compPow < min) {
					min = compPow;
				}
			}

			return min;
		}

		private double minPowerForCurrent(double current) {
			double min = Double.MAX_VALUE;

			for (int i = 0; i < n; i++) {
				double compPow = computingPower(p[i], c[i], current);
				if (compPow < min) {
					min = compPow;
				}
			}

			return min;
		}

		private double getResult() {
			//  Select bounds for binary search
			int left = Integer.MAX_VALUE;
			int right = -1;

			for (int i = 0; i < n; i++) {
				if (c[i] > right) {
					right = c[i];
				}
				if (c[i] < left) {
					left = c[i];
				}
			}

			int mid = (left + right) / 2;

			//  Even though binary search is done on current vector
			//  The update of the bounds relies on the system power
			int leftPow = minPowerForCurrent(mid - 1);
			int midPow = minPowerForCurrent(mid);
			int rightPow = minPowerForCurrent(mid + 1);

			while (left < right) {
				//  Stop binary search when we find the maximum avg power
				if (midPow >= leftPow && midPow >= rightPow) {
					break;
				}

				//  Update intervals
				if (midPow >  leftPow) {
					left = mid + 1;
				} else {
					right = mid - 1;
				}

				mid = (left + right) / 2;

				leftPow = minPowerForCurrent(mid - 1);
				midPow = minPowerForCurrent(mid);
				rightPow = minPowerForCurrent(mid + 1);
			}

			//  Extract the highest system power
			double maxPow = midPow;
			double powLeft = minPowerForCurrent(mid - 0.5);
			double powRight = minPowerForCurrent(mid + 0.5);

			if (maxPow < powLeft) {
				maxPow = powLeft;
			}
			if (maxPow < powRight) {
				maxPow = powRight;
			}
			return maxPow;
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
