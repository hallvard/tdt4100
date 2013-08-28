package wordfeud.core;

public class BoardDirection {

	public final int dx, dy;

	public BoardDirection(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public final static BoardDirection
		RIGHT = new BoardDirection(1, 0),
		DOWN = new BoardDirection(0, 1);
	
	public BoardDirection getPerpendicularDirection() {
		return getDirection(dy, dx);
	}
	
	public static BoardDirection getDirection(int dx, int dy) {
		if (dx > 0 && dy == 0) {
			return RIGHT;
		} else if (dy > 0 && dx == 0) {
			return DOWN;
		}
		return null;
	}
}
