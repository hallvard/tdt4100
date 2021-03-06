package wordfeud.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CellListBoard extends AbstractCellBoard {

	private List<Cell> cells = new ArrayList<Cell>();
	
	private int width = 0;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return width > 0 ? cells.size() / width : 0;
	}
	
	private int startX = -1, startY = -1;
	
	public Cell getCell(int x, int y) {
		int width = this.width > 0 ? this.width : cells.size();
		int pos = y * width + x;
		return pos < cells.size() ? cells.get(pos) : null; 
	}
	
	private void read(InputStream input) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = null;
		while ((line = reader.readLine()) != null) {
			if (width <= 0) {
				width = line.length();
			}
			for (int i = 0; i < width; i++) {
				char c = i < line.length() ? line.charAt(i) : ' ';
				int letterMultiple = 1, wordMultiple = 1;
				if (c == '�') {
					wordMultiple = 3;
				} else if (c == '3') {
					letterMultiple = 3;
				} else if (c == '$') {
					wordMultiple = 2;
				} else if (c == '2') {
					letterMultiple = 2;
				} else if (c == '*') {
					startX = i;
					startY = cells.size() / width;
				}
				cells.add(new Cell(letterMultiple, wordMultiple));
			}
		}
	}

	public CellListBoard(String resourcePath) {
		try {
			read(getClass().getResourceAsStream(resourcePath));
		} catch (IOException e) {
		}
	}
	
	public static CellListBoard load(String name) {
		return new CellListBoard("/wordfeud/" + name + ".board");
	}

	@Override
	public boolean isStart(int x, int y) {
		return x >= 0 && y >= 0 && x == startX && y == startY;
	}
}
