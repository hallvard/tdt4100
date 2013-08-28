package wordfeud.core;

public interface Letters extends Iterable<Character> {
	public int getCount(char letter);
	public int getPoints(char letter);
}
