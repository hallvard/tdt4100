package wordfeud.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import nsf.dict.io.NSFFile;
import wordfeud.core.SortedSetWords;

public class NSFWords extends SortedSetWords {

	private void read(InputStream input) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, "utf-8"));
		String line = null;
		while ((line = reader.readLine()) != null) {
			for (int i = 0; i < line.length(); i++) {
				if (! Character.isLetter(line.charAt(i))) {
					if (i > 1) {
						addWord(line.substring(0, i).toLowerCase());
					}
					break;
				}
			}
		}
	}

	protected NSFWords(InputStream input) {
		try {
			read(input);
		} catch (IOException e) {
		}
	}
	
	public static NSFWords create() {
		return new NSFWords(NSFFile.createStream());
	}
}
