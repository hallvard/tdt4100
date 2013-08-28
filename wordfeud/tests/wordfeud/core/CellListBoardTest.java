package wordfeud.core;

import junit.framework.TestCase;

public class CellListBoardTest extends TestCase {

	private Board board;

	@Override
	protected void setUp() throws Exception {
		board = CellListBoard.load("default");
	}

	public void testLoad() {
		assertEquals(15, board.getWidth());
		assertEquals(15, board.getHeight());
		for (int y = 0; y < board.getHeight(); y++) {
			for (int x = 0; x < board.getWidth(); x++) {
				assertEquals(x == 7 && y == 7, board.isStart(x, y));
			}
		}
		testMultiples(0, 0, 3, 1);
		testMultiples(0, 1, 1, 1);
		testMultiples(1, 0, 1, 1);
		testMultiples(1, 1, 2, 1);
		testMultiples(2, 2, 1, 2);
		testMultiples(3, 3, 3, 1);
		testMultiples(4, 4, 1, 2);
		testMultiples(0, 4, 1, 3);
		testMultiples(4, 0, 1, 3);
	}

	private void testMultiples(int x, int y, int lm, int wm) {
		assertEquals(lm, board.getLetterMultiple(x, y));
		assertEquals(wm, board.getWordMultiple(x, y));
		
		assertEquals(lm, board.getLetterMultiple(board.getWidth() - x - 1, y));
		assertEquals(wm, board.getWordMultiple(board.getWidth() - x - 1, y));
		
		assertEquals(lm, board.getLetterMultiple(x, board.getHeight() - y - 1));
		assertEquals(wm, board.getWordMultiple(x, board.getHeight() - y - 1));

		assertEquals(lm, board.getLetterMultiple(board.getWidth() - x - 1, board.getHeight() - y - 1));
		assertEquals(wm, board.getWordMultiple(board.getWidth() - x - 1, board.getHeight() - y - 1));
	}
}
