package wordfeud.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import wordfeud.WordfeudProgram;
import wordfeud.core.Piece;
import wordfeud.core.WildcardPiece;
import acm.graphics.GRect;

public class GCell extends GRect {

	public static Color LETTER_COLOR;
	public static Color PIECE_COLOR;
	public static Color TEMP_PIECE_COLOR;
	public static Color BACKGROUND_COLOR;
	public static Color DOUBLE_WORD_COLOR;
	public static Color TRIPLE_WORD_COLOR;
	public static Color DOUBLE_LETTER_COLOR;
	public static Color TRIPLE_LETTER_COLOR;
	public static Color MULTIPLE_LABEL_COLOR;
	public static Font MULTIPLE_FONT;
	public static Font LETTER_FONT;
	public static Font POINTS_FONT;

	protected final WordfeudProgram wordFeudProgram;
	
	private Piece piece;
	
	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
		repaint();
	}

	public GCell(double width, double height, WordfeudProgram wordFeudProgram) {
		super(width, height);
		setFillColor(BACKGROUND_COLOR);
		this.wordFeudProgram = wordFeudProgram;
	}

	@Override
	public void paint(Graphics g) {
		Rectangle r = getAWTBounds();
		paintBackground(g, r);
		paintForeground(g, r);
		g.setColor(Color.BLACK);
		g.drawRoundRect(r.x, r.y, r.width, r.height, 4, 4);
	}

	protected void paintBackground(Graphics g, Rectangle r) {
		g.setColor(getFillColor());
		g.fillRect(r.x, r.y, r.width, r.height);
	}

	protected void paintForeground(Graphics g, Rectangle r) {
		Piece piece = getPiece();
		if (piece != null) {
			paintPiece(g, r, piece, PIECE_COLOR);
		}
	}
	
	protected void paintPiece(Graphics g, Rectangle r, Piece piece, Color pieceColor) {
		g.setColor(pieceColor);
		g.fillRoundRect(r.x, r.y, r.width, r.height, 2, 2);
		g.setColor(LETTER_COLOR);
		char letter = piece.getLetter();
		drawString(g, LETTER_FONT, String.valueOf(letter == Piece.NO_LETTER ? ' ' : letter), -0.1f, 0.2f);
		if (! (piece instanceof WildcardPiece)) {
			drawString(g, POINTS_FONT, String.valueOf(wordFeudProgram.getWordFeud().getLetters().getPoints(letter)), 0.4f, -0.4f);
		}
	}

	protected void drawString(Graphics g, Font font, String s, float xf, float yf) {
		Rectangle r = getAWTBounds();
		FontMetrics fontMetrics = g.getFontMetrics(font);
		int x = (int)(r.x + r.width * (xf + 0.5f)), y = (int)(r.y + r.height * (yf + 0.5f));
		int width = fontMetrics.stringWidth(s), height = fontMetrics.getHeight();
		x -= width * (xf + 0.5f);
		y -= height * (yf + 0.5f);
		g.setFont(font);
		g.drawString(s, x, y + fontMetrics.getLeading() + fontMetrics.getAscent());
	}
}
