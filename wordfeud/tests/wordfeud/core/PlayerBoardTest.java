package wordfeud.core;

import java.util.Iterator;

import junit.framework.TestCase;

public class PlayerBoardTest extends TestCase {

	private MutableBoard sharedBoard;
	private PlayerBoard board1, board2;
	
	@Override
	protected void setUp() throws Exception {
		sharedBoard = CellListBoard.load("default");
		board1 = new PlayerBoard(sharedBoard);
		board1.newRound(0);
		board2 = new PlayerBoard(sharedBoard);
		board2.newRound(0);
	}

	public void testGetPiece() {
		Piece piece = new Piece('a', 1);
		sharedBoard.setPiece(7, 7, piece);
		assertEquals(piece, board1.getPiece(7, 7));
		assertEquals(piece, board2.getPiece(7, 7));
	}
	
	public void testSetPiece() {
		Piece piece = new Piece('a', 1);
		board1.setPiece(7, 7, piece);
		assertEquals(piece, board1.getPiece(7, 7));
		assertEquals(piece, board2.getPiece(7, 7));
	}
	
	public void testPlacePiece() {
		Piece piece = new Piece('a', 1);
		BoardPiece boardPiece = new BoardPiece(0, piece, new BoardLocation(7, 7));
		board1.placePiece(piece, 7, 7);
		assertEquals(piece, board1.getPiece(7, 7));
		assertEquals(null, board2.getPiece(7, 7));
		assertEquals(boardPiece, board1.getPlayerRound().getBoardPiece(piece));
		assertEquals(boardPiece, board1.getPlayerRound().getBoardPiece(7, 7));
		Iterator<BoardPiece> iterator = board1.getPlayerRound().iterator();
		assertTrue(iterator.hasNext());
		assertEquals(boardPiece, iterator.next());
		assertFalse(iterator.hasNext());
	}
}
