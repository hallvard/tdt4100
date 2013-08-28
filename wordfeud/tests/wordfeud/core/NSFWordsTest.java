package wordfeud.core;

import junit.framework.TestCase;
import wordfeud.io.NSFWords;

public class NSFWordsTest extends TestCase {

	private NSFWords words;

	@Override
	protected void setUp() throws Exception {
		words = NSFWords.create();
	}

	public void testNorskBM() {
		assertTrue(words.hasWord("kjoleliva"));
		assertTrue(! words.hasWord("skjoleliva"));
		assertTrue(words.hasWord("wc"));
		assertTrue(! words.hasWord("WC"));
	}
}
