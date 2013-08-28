package wordfeud.core;

public class Cell {

	public final int letterMultiple, wordMultiple;

	public Cell(int letterMultiple, int wordMultiple) {
		super();
		this.letterMultiple = letterMultiple;
		this.wordMultiple = wordMultiple;
	}
	
	private Piece piece = null;
	
	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}
}
