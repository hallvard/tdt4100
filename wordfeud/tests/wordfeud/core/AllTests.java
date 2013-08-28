package wordfeud.core;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(CellListBoardTest.class);
		suite.addTestSuite(LanguageLettersTest.class);
		suite.addTestSuite(OrdbankWordsTest.class);
		suite.addTestSuite(PlayerBoardTest.class);
		suite.addTestSuite(WordFeudTest.class);
		//$JUnit-END$
		return suite;
	}

}
