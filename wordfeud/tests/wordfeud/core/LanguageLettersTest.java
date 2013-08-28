package wordfeud.core;

import junit.framework.TestCase;

public class LanguageLettersTest extends TestCase {

	private LanguageLetters letters;

	@Override
	protected void setUp() throws Exception {
		letters = LanguageLetters.load("nb");
	}

	public void testLoad() {
		assertEquals(7, letters.getCount('A'));
		assertEquals(1, letters.getPoints('A'));
		assertEquals(2, letters.getCount('Å'));
		assertEquals(4, letters.getPoints('Å'));
	}
}
