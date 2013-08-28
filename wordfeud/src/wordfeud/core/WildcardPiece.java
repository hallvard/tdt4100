package wordfeud.core;

public class WildcardPiece extends Piece {

	public WildcardPiece(int num) {
		super(NO_LETTER, num);
	}
	
	public String toString() {
		return "*" + num + "=" + getLetter();
	}
	
	@Override
	public String getId() {
		return "*" + num;
	}

	public void setLetter(char letter) {
		if (! Character.isLetter(letter)) {
			throw new IllegalArgumentException("'" + letter + "' is not a letter");
		}
		this.letter = Character.toLowerCase(letter);
	}
	
	public void clearLetter(char letter) {
		this.letter = NO_LETTER;
	}

	@Override
	public int getPoints(WordFeud wordFeud) {
		return 0;
	}
}
