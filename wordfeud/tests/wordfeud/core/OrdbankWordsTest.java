package wordfeud.core;

import wordfeud.io.OrdbankWords;
import junit.framework.TestCase;

public class OrdbankWordsTest extends TestCase {

	private OrdbankWords words;

	@Override
	protected void setUp() throws Exception {
		words = OrdbankWords.create();
	}

	public void testNorskBM() {
		assertTrue(words.hasWord("kjoleliva"));
		assertTrue(! words.hasWord("skjoleliva"));
		assertTrue(words.hasWord("ca"));
		assertTrue(words.hasWord("wc"));
		assertTrue(! words.hasWord("WC"));
	}

}
