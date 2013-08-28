package wordfeud.core;

public class PlayerBoard extends AbstractMutableBoard {

	private final MutableBoard board;
	private PlayerRound playerRound;
	
	public PlayerBoard(MutableBoard board) {
		super();
		this.board = board;
	}
	
	public PlayerRound newRound(int roundNum) {
		PlayerRound oldRound = playerRound;
		playerRound = new PlayerRound(board, roundNum);
		return oldRound;
	}
	
	public void placePiece(Piece piece, int x, int y) {
		removePiece(piece);
		playerRound.placePiece(piece, x, y);
		fireBoardChanged(x, y);
	}

	public void removePiece(Piece piece) {
		BoardPiece existing = playerRound.removePiece(piece);
		if (existing != null) {
			fireBoardChanged(existing.getLocation().x, existing.getLocation().y);
		}
	}
	
	public PlayerRound getPlayerRound() {
		return playerRound;
	}
	
	// delegate all methods to board
	
	@Override
	public int getWidth() {
		return board.getWidth();
	}

	@Override
	public int getHeight() {
		return board.getHeight();
	}

	@Override
	public int getLetterMultiple(int x, int y) {
		return board.getLetterMultiple(x, y);
	}

	@Override
	public int getWordMultiple(int x, int y) {
		return board.getWordMultiple(x, y);
	}

	@Override
	public boolean isStart(int x, int y) {
		return board.isStart(x, y);
	}

	@Override
	public Piece getPiece(int x, int y) {
		BoardPiece boardPiece = playerRound.getBoardPiece(x, y);
		return (boardPiece != null ? boardPiece.getPiece() : board.getPiece(x, y));
	}
	
	@Override
	public void setPiece(int x, int y, Piece piece) {
		board.setPiece(x, y, piece);
	}
	
	//

	@Override
	public void addBoardListener(BoardListener listener) {
		super.addBoardListener(listener);
		board.addBoardListener(listener);
	}

	@Override
	public void removeBoardListener(BoardListener listener) {
		super.removeBoardListener(listener);
		board.removeBoardListener(listener);
	}
}
