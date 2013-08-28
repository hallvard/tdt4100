package wordfeud.core;

public abstract class AbstractCellBoard extends AbstractMutableBoard {

	public abstract Cell getCell(int x, int y);
	
	@Override
	public int getLetterMultiple(int x, int y) {
		Cell cell = getCell(x, y);
		return cell != null ? cell.letterMultiple : 0;
	}

	@Override
	public int getWordMultiple(int x, int y) {
		Cell cell = getCell(x, y);
		return cell != null ? cell.wordMultiple : 0;
	}

	@Override
	public Piece getPiece(int x, int y) {
		Cell cell = getCell(x, y);
		return cell != null ? cell.getPiece() : null;
	}
	
	@Override
	public void setPiece(int x, int y, Piece piece) {
		Cell cell = getCell(x, y);
		if (cell != null) {
			cell.setPiece(piece);
		}
		fireBoardChanged(x, y);
	}
}
