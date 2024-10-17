import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;

public class Criptat {
	public static class Word {
		int weight;
		int price;

		public void setWeight(int weight) {
			this.weight = weight;
		}

		public void setPrice(int price) {
			this.price = price;
		}
	}

	public static final class Task {
		public static final String INPUT_FILE = "criptat.in";
		public static final String OUTPUT_FILE = "criptat.out";

		int n;
		ArrayList<Word> pass;
		String[] words;

		//  All letters of the alphabet
		int[] letters = new int[26];

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Reader reader = new FileReader(INPUT_FILE);
				MyScanner sc = new MyScanner(reader);

				n = sc.nextInt();
				words = new String[n];
				pass = new ArrayList<>();

				for (int i = 0; i < n; i++) {
					words[i] = sc.next();
					pass.add(new Word());
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

		//  Count the occurrence of a letter in a string
		private int countOccurrences(String str, char let) {
			int count = 0;
			for (char c: str.toCharArray()) {
				if (c == let) {
					count++;
				}
			}
			return count;
		}

		//  Count all letters from one string
		private void countAllLetters(String str) {
			for (char c: str.toCharArray()) {
				letters[c - 'a']++;
			}
		}

		private int getResult() {
			//  Take input and establish letter frequencies
			for (int i = 0; i < n; i++) {
				countAllLetters(words[i]);
			}

			int maxW = 0;

			//  Parse the alphabet to treat all cases
			for (int crtLetter = 0; crtLetter < 26; crtLetter++) {
				//  Skip letters that don't exist
				if (letters[crtLetter] == 0) {
					continue;
				}

				//  Calculate price depending on crt letter
				//  Price == letter occurrence
				for (int i = 0; i < n; i++) {
					pass.get(i).setPrice(countOccurrences(words[i], (char) (crtLetter + 'a')));
					pass.get(i).setWeight(words[i].length());
				}

				//  Sort words by (price / weight) ratio
				pass.sort((o1, o2) -> {
					float ratio1 = (float) o1.price / o1.weight;
					float ratio2 = (float) o2.price / o2.weight;

					if (ratio1 > ratio2) {
						return -1;
					} else if (ratio1 < ratio2) {
						return 1;
					}

					return 0;
				});

				int w = 0;
				int p = 0;

				//  Iterate and get best p / w ratio password
				for (Word word: pass) {
					int elemW = word.weight;
					int elemP = word.price;

					if (p + elemP > (w + elemW) / 2) {
						p += elemP;
						w += elemW;
					}
				}

				if (maxW < w) {
					maxW = w;
				}
			}

			return maxW;
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
