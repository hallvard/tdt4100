package wordfeud.core;

public class BoardPiece implements Points {
	
	private int roundUsed;
	private final Piece piece;
	private final BoardLocation location;

	public BoardPiece(int roundUsed, Piece piece, BoardLocation location) {
		super();
		this.roundUsed = roundUsed;
		this.piece = piece;
		this.location = location;
	}

	public String toString() {
		return piece + "@" + location;
	}
	
	public Piece getPiece() {
		return piece;
	}

	public BoardLocation getLocation() {
		return location;
	}

	@Override
	public int getPoints(WordFeud wordFeud) {
		Board board = wordFeud.getBoard();
		int points = wordFeud.getLetters().getPoints(piece.getLetter());
		if (usedWhenPlayed()) {
			points *= board.getLetterMultiple(location.x, location.y);
		}
		return points;
	}

	public boolean usedWhenPlayed() {
		return roundUsed == piece.getRoundPlayed();
	}
}
