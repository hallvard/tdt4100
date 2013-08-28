package wordfeud.core;

public class Piece implements Points {
	
	public static final char NO_LETTER = '\0';
	
	private int roundPlayed = -1;

	protected char letter;
	protected final int num;
	
	public Piece(char letter, int num) {
		this.letter = Character.toUpperCase(letter);
		this.num = num;
	}
	
	public String toString() {
		return String.valueOf(letter) + num;
	}
	
	public String getId() {
		return toString();
	}

	public char getLetter() {
		return letter;
	}

	//

	public int getRoundPlayed() {
		return roundPlayed;
	}

	public void setRoundPlayed(int n) {
		roundPlayed = n;
	}

	@Override
	public int getPoints(WordFeud wordFeud) {
		return wordFeud.getLetters().getPoints(getLetter());
	}
}
