package wordfeud.core;

public final class BoardLocation implements Comparable<BoardLocation> {

	public final int x, y;

	public BoardLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return x + "," + y;
	}

	@Override
	public int hashCode() {
		return x * 31 + y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		BoardLocation other = (BoardLocation) obj;
		return (x == other.x && y == other.y);
	}

	@Override
	public int compareTo(BoardLocation other) {
		int diff = this.x - other.x;
		if (diff == 0) {
			diff = this.y - other.y;
		}
		return diff;
	}
}
