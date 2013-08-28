package wordfeud.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LanguageLetters extends AbstractLetters {

	private void read(InputStream input) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = null;
		while ((line = reader.readLine()) != null) {
			StringTokenizer tokens = new StringTokenizer(line);
			while (tokens.hasMoreTokens()) {
				String c = tokens.nextToken(), count = tokens.nextToken(), points = tokens.nextToken();
				setLetterValues(c.charAt(0), Integer.valueOf(count), Integer.valueOf(points));
			}
		}
	}

	public LanguageLetters(String resourcePath) {
		try {
			read(getClass().getResourceAsStream(resourcePath));
		} catch (IOException e) {
		}
	}
	
	public static LanguageLetters load(String lang) {
		return new LanguageLetters("/wordfeud/" + lang + ".letters");
	}
}
