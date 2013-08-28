package wordfeud.core;

public class BoardWord implements CharSequence, Points {
	
	private final Board board;
	private final int roundPlayed;
	private final int x, y, length;
	private final BoardDirection direction;

	private BoardWord(Board board, int roundPlayed, int x, int y, BoardDirection direction, int length) {
		this.board = board;
		this.roundPlayed = roundPlayed;
		this.direction = direction;
		this.x = x;
		this.y = y;
		this.length = length;
	}

	public BoardWord(Board board, int roundPlayed, int x, int y, BoardDirection direction) {
		this.board = board;
		this.roundPlayed = roundPlayed;
		this.direction = direction;
		while (x >= 0 && y >= 0) {
			Piece piece = board.getPiece(x - direction.dx, y - direction.dy);
			if (piece == null || piece.getRoundPlayed() > roundPlayed) {
				break;
			}
			x -= direction.dx;
			y -= direction.dy;
		}
		int length = 0;
		while (x + length * direction.dx < board.getWidth() && y + length * direction.dy < board.getHeight()) {
			Piece piece = board.getPiece(x + length * direction.dx, y + length * direction.dy);
			if (piece == null || piece.getRoundPlayed() > roundPlayed) {
				break;
			}
			length++;
		}
		this.x = x;
		this.y = y;
		this.length = length;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BoardDirection getDirection() {
		return direction;
	}
	
	public String toString() {
		StringBuilder buffer = new StringBuilder(this);
		buffer.append(":");
		for (int i = 0; i < length(); i++) {
			buffer.append(" ");
			buffer.append(getPiece(i));
		}
		return buffer.toString();
	}

	private Piece getPiece(int i) {
		return board.getPiece(x + direction.dx * i, y + direction.dy * i);
	}

	@Override
	public int getPoints(WordFeud wordFeud) {
		int points = 0, multiple = 1;
		for (int i = 0; i < length(); i++) {
			int x = this.x + direction.dx * i, y = this.y + direction.dy * i;
			Piece piece = board.getPiece(x, y);
			points += piece.getPoints(wordFeud);
			if (piece.getRoundPlayed() == roundPlayed) {
				multiple *= board.getWordMultiple(x, y);
			}
		}
		return points * multiple;
	}
	
	// from CharSequence
	
	@Override
	public char charAt(int i) {
		return getPiece(i).getLetter();
	}

	@Override
	public int length() {
		return length;
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		if (start < 0 || start >= length()) {
			throw new IndexOutOfBoundsException(String.format("The start value (%s) must be in the range [%s - %s)", start, 0, length())); 
		}
		if (end < start || end > length()) {
			throw new IndexOutOfBoundsException(String.format("The end value (%s) must be in the range [%s - %s]", end, start, length())); 
		}
		return new BoardWord(board, roundPlayed, x + direction.dx * start, y + direction.dy * start, direction, end - start);
	}
}
