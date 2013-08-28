package wordfeud.core;

import junit.framework.TestCase;

public class BoardWordTest extends TestCase {

	private MutableBoard board;

	@Override
	protected void setUp() throws Exception {
		board = CellListBoard.load("default");
	}
	
	private void placeWord(String s, int x, int y, int dx, int dy) {
		for (int i = 0; i < s.length(); i++) {
			board.setPiece(x, y, new Piece(s.charAt(i), 0));
			x += dx;
			y += dy;
		}
	}
	
	private void testBoardWord(BoardWord boardWord, String s) {
		int length = boardWord.length();
		assertEquals(s.length(), length);
		for (int i = 0; i < length; i++) {
			assertEquals(Character.toUpperCase(s.charAt(i)), boardWord.charAt(i));
		}
	}

	public void testBoardWord() {
		placeWord("test", 5, 5, 1, 0);
		placeWord("test", 5, 5, 0, 1);

		testBoardWord(new BoardWord(board, 0, 5, 5, BoardDirection.RIGHT), "test");
		testBoardWord(new BoardWord(board, 0, 5, 5, BoardDirection.DOWN), "test");
		testBoardWord(new BoardWord(board, 0, 8, 5, BoardDirection.RIGHT), "test");
		testBoardWord(new BoardWord(board, 0, 5, 8, BoardDirection.DOWN), "test");
	}
}
