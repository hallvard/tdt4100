package wordfeud.core;

import java.util.SortedSet;
import java.util.TreeSet;

public abstract class SortedSetWords implements Words {

	private SortedSet<CharSequence> wordSet = new TreeSet<CharSequence>();
	
	protected void addWord(CharSequence word) {
		if (isLegalWord(word)) {
			wordSet.add(word);
		}
	}
	
	public boolean isLegalWord(CharSequence word) {
		int count = word.length(), lowerCount = 0, upperCount = 0;
		for (int i = 0; i < count; i++) {
			char c = word.charAt(i);
			if (! Character.isLetter(c)) {
				return false;
			}
			if (Character.isLowerCase(c)) {
				lowerCount++;
			} else if (Character.isUpperCase(c)) {
				upperCount++;
			}
		}
		return isLegalWord(count, lowerCount, upperCount);
	}

	private boolean isLegalWord(int count, int lowerCount, int upperCount) {
		return lowerCount == count;
	}
	
	@Override
	public boolean hasWord(CharSequence word) {
		return wordSet.contains(word);
	}
}
