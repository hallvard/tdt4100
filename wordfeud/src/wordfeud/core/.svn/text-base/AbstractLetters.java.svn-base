package wordfeud.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractLetters implements Letters {

	private Map<Character, Integer> counts = new HashMap<Character, Integer>();
	private Map<Character, Integer> points = new HashMap<Character, Integer>();
	
	@Override
	public Iterator<Character> iterator() {
		return counts.keySet().iterator();
	}

	protected void setLetterValues(char letter, int count, int points) {
		letter = Character.toLowerCase(letter);
		this.counts.put(letter, count);
		this.points.put(letter, points);
	}
	
	@Override
	public int getCount(char letter) {
		letter = Character.toLowerCase(letter);
		return counts.containsKey(letter) ? counts.get(letter) : 0;
	}

	@Override
	public int getPoints(char letter) {
		letter = Character.toLowerCase(letter);
		return points.containsKey(letter) ? points.get(letter) : 0;
	}
}
