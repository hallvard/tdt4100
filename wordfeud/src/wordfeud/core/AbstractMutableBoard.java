package wordfeud.core;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMutableBoard implements MutableBoard {

	private List<BoardListener> boardListeners = new ArrayList<BoardListener>();
	
	@Override
	public void addBoardListener(BoardListener listener) {
		boardListeners.add(listener);
	}

	@Override
	public void removeBoardListener(BoardListener listener) {
		boardListeners.remove(listener);
	}
	
	protected void fireBoardChanged(int x, int y) {
		for (BoardListener listener : boardListeners) {
			listener.boardChanged(x, y);
		}
	}
}
