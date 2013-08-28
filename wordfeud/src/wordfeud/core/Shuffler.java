package wordfeud.core;

import java.util.List;

public interface Shuffler<T> {
	public void shuffle(List<T> elements, int times);
}
