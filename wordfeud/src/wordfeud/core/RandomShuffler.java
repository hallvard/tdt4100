package wordfeud.core;

import java.util.List;

public class RandomShuffler<T> implements Shuffler<T> {

	@Override
	public void shuffle(List<T> elements, int times) {
		while (times-- > 0) {
			for (int i = 0; i < elements.size(); i++) {
				T element = elements.get(i);
				int pos = (int)(Math.random() * elements.size());
				elements.set(i, elements.get(pos));
				elements.set(pos, element);
			}
		}
	}
}
