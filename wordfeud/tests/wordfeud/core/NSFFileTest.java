package wordfeud.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.TestCase;
import nsf.dict.io.NSFFile;

public class NSFFileTest extends TestCase {

	private NSFFile words;

	@Override
	protected void setUp() throws Exception {
		words = NSFFile.create();
	}

	public void testNorskBM() {
		assertTrue(words.hasWord("ab"));
		assertTrue(words.hasWord("kjoleliva"));
		assertTrue(! words.hasWord("skjoleliva"));
		assertTrue(words.hasWord("wc"));
	}

	public void testAll() throws IOException {
		InputStream input = NSFFile.class.getResourceAsStream("/wordfeud/NSF-ordlisten.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, "utf-8"));
		String line = null;
		while ((line = reader.readLine()) != null) {
			int pos = line.indexOf(' ');
			if (pos > 0) {
				line = line.substring(0, pos);
			}
			if (! words.hasWord(line)) {
				words.hasWord(line);
				fail(line + " not found");
			}
		}
	}
}
