package wordfeud.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import wordfeud.WordfeudProgram;
import wordfeud.core.Board;
import wordfeud.core.Piece;

public class GBoardCell extends GCell {
	
	private static final int TRANSPARENCY = 200;

	private final int boardX, boardY;

	public GBoardCell(double width, double height, WordfeudProgram wordFeudProgram, int x, int y) {
		super(width, height, wordFeudProgram);
		this.boardX = x;
		this.boardY = y;
	}

	public int getBoardX() {
		return boardX;
	}

	public int getBoardY() {
		return boardY;
	}

	private Color getBackgroundColor() {
		Color multipleColor = null;
		Board board = wordFeudProgram.getWordFeud().getBoard();
		if (board.getLetterMultiple(boardX, boardY) == 3) {
			multipleColor = TRIPLE_LETTER_COLOR;
		} else if (board.getLetterMultiple(boardX, boardY) == 2) {
			multipleColor = DOUBLE_LETTER_COLOR;
		} else if (board.getWordMultiple(boardX, boardY) == 3) {
			multipleColor = TRIPLE_WORD_COLOR;
		} else if (board.getWordMultiple(boardX, boardY) == 2) {
			multipleColor = DOUBLE_WORD_COLOR;
		}
		return multipleColor;
	}
	
	private String getBackgroundLabel() {
		String backgroundLabel = null;
		Board board = wordFeudProgram.getWordFeud().getBoard();
		if (board.getLetterMultiple(boardX, boardY) == 3) {
			backgroundLabel = "3L";
		} else if (board.getLetterMultiple(boardX, boardY) == 2) {
			backgroundLabel = "2L";
		} else if (board.getWordMultiple(boardX, boardY) == 3) {
			backgroundLabel = "3W";
		} else if (board.getWordMultiple(boardX, boardY) == 2) {
			backgroundLabel = "2W";
		} else if (board.isStart(boardX, boardY)) {
			backgroundLabel = "*";
		}
		return backgroundLabel;
	}

	@Override
	protected void paintBackground(Graphics g, Rectangle r) {
		super.paintBackground(g, r);
		if (boardY >= 0) {
			Color multipleColor = getBackgroundColor();
			if (multipleColor != null) {
				g.setColor(multipleColor);
				g.fillRoundRect(r.x, r.y, r.width, r.height, 4, 4);
			}
			String backgroundLabel = getBackgroundLabel();
			if (backgroundLabel != null) {
				g.setColor(MULTIPLE_LABEL_COLOR);
				drawString(g, MULTIPLE_FONT, backgroundLabel, 0.0f, 0.0f);
			}
		}
	}

	private Map<Color, Color> transparentColors = new HashMap<Color, Color>();
	
	@Override
	protected void paintPiece(Graphics g, Rectangle r, Piece piece, Color pieceColor) {
		if (pieceColor == null) {
			pieceColor = PIECE_COLOR;
		}
		if (wordFeudProgram.getWordFeud().isPlacedPiece(piece)) {
			pieceColor = TEMP_PIECE_COLOR;
		}
		if (getBackgroundColor() != null) {
			Color transparentColor = transparentColors.get(pieceColor);
			if (transparentColor == null) {
				transparentColor = new Color(pieceColor.getRed(), pieceColor.getGreen(), pieceColor.getBlue(), TRANSPARENCY);
				transparentColors.put(pieceColor, transparentColor);
			}
			pieceColor = transparentColor;
		}
		super.paintPiece(g, r, piece, pieceColor);
	}
}
