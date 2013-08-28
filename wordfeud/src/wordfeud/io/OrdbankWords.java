package wordfeud.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import wordfeud.core.SortedSetWords;

public class OrdbankWords extends SortedSetWords {

	private void read(InputStream input) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, "iso-8859-1"));
		String line = null;
		while ((line = reader.readLine()) != null) {
			char[] chars = line.toCharArray();
			int tokenCount = 0, start = 0, end = -1;
			String word = null, wordClass = null;
			while (start < line.length()) {
				end = line.indexOf('\t', start);
				if (end < 0) {
					end = line.length();
				}
				tokenCount++;
				if (tokenCount == 3) {
					word = new String(chars, start, end - start);
				} else if (tokenCount == 4) {
					wordClass = new String(chars, start, end - start);
				}
				start = end + 1;
			}
			if (word != null && word.length() > 0) {
				if (isLegalWordClass(wordClass)) {
					addWord(word.toLowerCase());
				}
			}
		}
	}

	private boolean isLegalWordClass(String wordClass) {
		return (! ("fork".equals(wordClass) || "symb".equals(wordClass)));
	}
	
	protected OrdbankWords(String resourcePath) {
		try {
			read(getClass().getResourceAsStream(resourcePath));
		} catch (IOException e) {
		}
	}
	
	public static OrdbankWords create() {
		return new OrdbankWords("/wordfeud/ordbank_bm/fullform_bm.txt");
	}
}
