package wordfeud;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JTextField;

import ui.GGrid;
import wordfeud.core.Board;
import wordfeud.core.BoardListener;
import wordfeud.core.BoardLocation;
import wordfeud.core.Piece;
import wordfeud.core.Player;
import wordfeud.core.WildcardPiece;
import wordfeud.core.WordFeud;
import wordfeud.core.WordFeudListener;
import wordfeud.ui.GBoardCell;
import wordfeud.ui.GCell;
import acm.graphics.GContainer;
import acm.graphics.GDimension;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

public class WordfeudProgram extends GraphicsProgram implements BoardListener, WordFeudListener {

	private GGrid boardGrid;
	private GGrid piecesGrid;
	private WordFeud wordFeud;
	private GDimension cellSize;

	public void init() {
		GCell.LETTER_COLOR = Color.BLACK;
		GCell.PIECE_COLOR = Color.WHITE;
		GCell.TEMP_PIECE_COLOR = Color.YELLOW;
		GCell.BACKGROUND_COLOR = Color.DARK_GRAY;
		GCell.DOUBLE_WORD_COLOR = Color.ORANGE;
		GCell.TRIPLE_WORD_COLOR = Color.MAGENTA;
		GCell.DOUBLE_LETTER_COLOR = Color.GREEN;
		GCell.TRIPLE_LETTER_COLOR = Color.BLUE;
		GCell.MULTIPLE_LABEL_COLOR = Color.WHITE;

		GCell.MULTIPLE_FONT = Font.decode("Courier-BOLD-16");
		GCell.LETTER_FONT = Font.decode("Arial-PLAIN-28");
		GCell.POINTS_FONT = Font.decode("Arial-PLAIN-10");

		wordFeud = new WordFeud(1);
//		println(wordFeud.getWords().hasWord("bok"));
//		println(wordFeud.getLetters().getCount('a') + "~" + wordFeud.getLetters().getPoints('a'));
//		println(wordFeud.getLetters().getCount('Œ') + "~" + wordFeud.getLetters().getPoints('Œ'));
		Board board = wordFeud.getBoard();
		boardGrid = new GGrid(board.getWidth(), board.getHeight());
		cellSize = new GDimension(40, 40);
		boardGrid.setCellSize(cellSize.getWidth(), cellSize.getHeight());

		for (int y = 0; y < board.getHeight(); y++) {
			for (int x = 0; x < board.getWidth(); x++) {
				GCell cell = new GBoardCell(cellSize.getWidth(), cellSize.getHeight(), this, x, y);
				cell.setPiece(wordFeud.getBoard().getPiece(x, y));
				boardGrid.setCellObject(x, y, cell);
			}
		}
		add(boardGrid);

		piecesGrid = new GGrid(7, 1);
		piecesGrid.setCellSize(cellSize.getWidth(), cellSize.getHeight());
		piecesGrid.setLocation(50, boardGrid.getHeight() + 10);
		for (int pos = 0; pos < 7; pos++) {
			GCell cell = new GCell(cellSize.getWidth(), cellSize.getHeight(), this);
			cell.setPiece(wordFeud.getPlayer(wordFeud.getCurrentPlayerNum()).getPiece(pos));
			piecesGrid.setCellObject(pos, 0, cell);
		}
		add(piecesGrid);

		getGCanvas().setSize((int) boardGrid.getWidth(), (int) (piecesGrid.getY() + piecesGrid.getHeight() + 5));
		Dimension canvasSize = getGCanvas().getSize();
//		getGCanvas().setAutoRepaintFlag(true);

		board.addBoardListener(this);
		wordFeud.addWordFeudListener(this);
		
		dragging = new GCell(cellSize.getWidth(), cellSize.getHeight(), this);

//		JPanel buttons = new JPanel();
		add(new JButton("Play"), SOUTH);
		add(new JButton("Clear"), SOUTH);
		add(new JButton("Shuffle"), SOUTH);
		
		remotePlayerText = new JTextField("<commands for remote player: Ln@x,y *>");
		add(remotePlayerText, NORTH);
		add(new JButton("Play remote"), NORTH);

		setSize((int) canvasSize.getWidth(), (int) canvasSize.getHeight() + 60);
		
		addMouseListeners();
		addKeyListeners();
		addActionListeners();
	}

	public void run() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("Play".equals(e.getActionCommand())) {
			wordFeud.playPieces();
		} else if ("Clear".equals(e.getActionCommand())) {
			wordFeud.placePiecesBack();
		} else if ("Shuffle".equals(e.getActionCommand())) {
			wordFeud.shufflePieces();
		} else if ("Play remote".equals(e.getActionCommand())) {
			wordFeud.playPieces("Player " + wordFeud.getCurrentPlayerNum(), parseBoardPieces(remotePlayerText.getText()));
		}
	}

	private Map<String, BoardLocation> parseBoardPieces(String text) {
		String[] boardPieces = text.split(" ");
		Map<String, BoardLocation> pieceLocations = new HashMap<String, BoardLocation>();
		for (int i = 0; i < boardPieces.length; i++) {
			String boardPiece = boardPieces[i];
			int pos1 = boardPiece.indexOf('@'), pos2 = boardPiece.indexOf(',', pos1);
			String pieceId = boardPiece.substring(0, pos1);
			int x = Integer.valueOf(boardPiece.substring(pos1 + 1, pos2)), y = Integer.valueOf(boardPiece.substring(pos2 + 1));
			pieceLocations.put(pieceId, new BoardLocation(x, y));
		}
		return pieceLocations;
	}

	public WordFeud getWordFeud() {
		return wordFeud;
	}
	
	private GCell origin, dragging, target;
	private GPoint originPosition;
	private GPoint lastPoint = null;
	private JTextField remotePlayerText;
	
	public boolean isDragging() {
		return origin != null && lastPoint != null;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		origin = (GCell) boardGrid.getElementAt(e.getX() - boardGrid.getX(), e.getY() - boardGrid.getY());
		if (origin == null) {
			origin = (GCell) piecesGrid.getElementAt(e.getX() - piecesGrid.getX(), e.getY() - piecesGrid.getY());
		}
		target = null;
		if (origin != null && origin.getPiece() != null) {
			GContainer parent = origin.getParent();
			if (parent instanceof GGrid) {
				originPosition = ((GGrid) parent).convertToGrid(e.getX(), e.getY());
//				if (parent == boardGrid) {
//					wordFeud.getBoard().setPiece((int) position.getX(), (int) position.getY(), null);
//				} else if (parent == piecesGrid) {
//					wordFeud.takePiece((int) position.getX());
//				}
			}
			GPoint location = origin.getLocation();
			if (parent instanceof GGrid) {
				location = ((GGrid) parent).getCanvasPoint(location);
			}
			dragging.setLocation(location);
			dragging.setPiece(origin.getPiece());
			origin.setPiece(null);
			getGCanvas().add(dragging);
			lastPoint = new GPoint(e.getX(), e.getY());
			e.consume();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (isDragging()) {
			if (lastPoint != null) {
				dragging.move(e.getX() - lastPoint.getX(), e.getY() - lastPoint.getY());
			}
			lastPoint = new GPoint(e.getX(), e.getY());
			e.consume();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isDragging()) {
			getGCanvas().remove(dragging);
			target = (GCell) boardGrid.getElementAt(e.getX() - boardGrid.getX(), e.getY() - boardGrid.getY());
			if (target == null) {
				target = (GCell) piecesGrid.getElementAt(e.getX() - piecesGrid.getX(), e.getY() - piecesGrid.getY());
			}
			if (target == null) {
				target = origin;
			}
			if (target != null) {
				GContainer originParent = origin.getParent(), cellParent = target.getParent();
				if (originParent instanceof GGrid && cellParent instanceof GGrid) {
					GPoint cellPosition = ((GGrid) cellParent).convertToGrid(e.getX(), e.getY());
					try {
						performMove(origin, originPosition, target, cellPosition);
					} catch (Exception e1) {
						origin.setPiece(dragging.getPiece());
					}
				}
			}
			getGCanvas().remove(dragging);
			lastPoint = null;
			e.consume();
		}
	}
	
	public void performMove(GCell source, GPoint sourcePosition, GCell target, GPoint targetPosition) {
		int sx = (int) sourcePosition.getX(), sy = (int) sourcePosition.getY();
		int tx = (int) targetPosition.getX(), ty = (int) targetPosition.getY();
		if (source.getParent() == piecesGrid && target.getParent() == boardGrid) {
			if (wordFeud.getBoard().getPiece(tx, ty) == null) {
				wordFeud.placePiece(sx, tx, ty);
			}
		} else if (source.getParent() == boardGrid && target.getParent() == boardGrid) {
			if (wordFeud.getBoard().getPiece(tx, ty) == null) {
				wordFeud.movePiece(sx, sy, tx, ty); 
			}
		} else if (source.getParent() == boardGrid && target.getParent() == piecesGrid) {
			wordFeud.placePieceBack(sx, sy, tx); 
		} else if (source.getParent() == piecesGrid && target.getParent() == piecesGrid) {
			wordFeud.movePiece(sx, tx);
		}
	}

	public void keyReleased(KeyEvent e) {
//		System.out.println("'" + e.getKeyChar() + "' = " + e.getKeyCode());
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			wordFeud.playPieces();
		} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			wordFeud.placePiecesBack();
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			wordFeud.shufflePieces();
		} else if (Character.isLetter(e.getKeyChar())) {
			if (target != null && target.getPiece() instanceof WildcardPiece) {
				WildcardPiece wildcard = (WildcardPiece) target.getPiece();
				wildcard.setLetter(e.getKeyChar());
				target.setPiece(wildcard);
			}
		} else {
			return;
		}
		e.consume();
	}
	
	@Override
	public void boardChanged(int x, int y) {
		((GCell) boardGrid.getCellObject(x, y)).setPiece(wordFeud.getBoard().getPiece(x, y));
	}

	@Override
	public void piecesChanged(int pos) {
		((GCell) piecesGrid.getCellObject(pos, 0)).setPiece(wordFeud.getCurrentPlayer().getPiece(pos));
	}

	@Override
	public void playerChanged(Player player) {
		Iterator<Piece> pieces = player.getPiecesIterator();
		StringBuilder buffer = new StringBuilder();
		while (pieces.hasNext()) {
			if (buffer.length() > 0) {
				buffer.append(" ");
			}
			buffer.append(pieces.next() + "@x,y");
		}
		remotePlayerText.setText(buffer.toString());
	}
}
