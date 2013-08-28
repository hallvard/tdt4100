package wordfeud.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class PlayerRound implements Points, Iterable<BoardPiece> {

	private Board board;
	private int roundNum;
	
	public PlayerRound(Board board, int roundNum) {
		this.board = board;
		this.roundNum = roundNum;
	}

	private List<BoardPiece> boardPieces = new ArrayList<BoardPiece>();
	private List<BoardWord> boardWords = new ArrayList<BoardWord>();

	public int getRoundNum() {
		return roundNum;
	}
	
	public Iterator<BoardPiece> iterator() {
		return boardPieces.iterator();
	}

	public Iterator<BoardWord> getBoardWords() {
		return boardWords.iterator();
	}

	public void placePiece(Piece piece, int x, int y) {
		removePiece(piece);
		boardPieces.add(new BoardPiece(roundNum, piece, new BoardLocation(x, y)));
		computeBoardWords(false);
	}

	public BoardPiece removePiece(Piece piece) {
		BoardPiece existing = getBoardPiece(piece);
		if (existing != null) {
			boardPieces.remove(existing);
			return existing;
		}
		return null;
	}
	
	public BoardPiece getBoardPiece(Piece piece) {
		for (BoardPiece boardPiece : boardPieces) {
			if (boardPiece.getPiece() == piece) {
				return boardPiece;
			}
		}
		return null;
	}

	public BoardPiece getBoardPiece(int x, int y) {
		for (BoardPiece boardPiece : boardPieces) {
			BoardLocation location = boardPiece.getLocation();
			if (location.x == x && location.y == y) {
				return boardPiece;
			}
		}
		return null;
	}

	@Override
	public int getPoints(WordFeud wordFeud) {
		int points = 0;
		for (BoardWord boardWord : boardWords) {
			points += boardWord.getPoints(wordFeud);
		}
		return points;
	}
	
	//

	private Comparator<BoardPiece> boardPieceComparator = new Comparator<BoardPiece>() {
		public int compare(BoardPiece boardPiece1, BoardPiece boardPiece2) {
			return boardPiece1.getLocation().compareTo(boardPiece2.getLocation());
		}
	};
	
	public boolean computeBoardWords(boolean throwError) {
		boardWords.clear();
		Collections.sort(boardPieces, boardPieceComparator);
		BoardLocation start = boardPieces.get(0).getLocation(), end = boardPieces.get(boardPieces.size() - 1).getLocation();
		int dx = (int) Math.signum(end.x - start.x), dy = (int) Math.signum(end.y - start.y);
		if (boardPieces.size() == 1) {
			dx = 1;
		}
		if (Math.signum(dx) + Math.signum(dy) != 1 && throwError) {
			throw new IllegalStateException("Illegal placements: " + boardPieces + ", the word must be either across or down");
		}
		int x = start.x, y = start.y;
		while (x <= end.x && y <= end.y) {
			if (board.getPiece(x, y) == null && throwError) {
				throw new IllegalStateException("Illegal placements: " + boardPieces + ", the letters must form a consecutive sequence");
			}
			x += dx;
			y += dy;
		}
		return computeBoardWords(start.x, start.y, BoardDirection.getDirection(dx, dy), true);
	}

	private boolean computeBoardWords(int x, int y, BoardDirection direction, boolean computeCrossingWords) {
		BoardWord boardWord = new BoardWord(board, roundNum, x, y, direction);
		x = boardWord.getX();
		y = boardWord.getY();
		boolean legalPlacement = false, error = false;
		while (x < board.getWidth() && y < board.getHeight()) {
			Piece piece = board.getPiece(x, y);
			if (piece == null) {
				break;
			}
			if (getBoardPiece(piece) != null) {
				if (computeCrossingWords) {
					if (! computeBoardWords(x, y, direction.getPerpendicularDirection(), false)) {
						error = true;
					} else {
						legalPlacement = true;
					}
				}
				if (board.isStart(x, y)) {
					legalPlacement = true;
				}
			} else {
				legalPlacement = true;
			}
			x += direction.dx;
			y += direction.dy;
		}
		if (boardWord.length() > 1) {
			boardWords.add(boardWord);
		}
		if (boardWords.size() > 0 && computeCrossingWords && (! legalPlacement)) {
			throw new IllegalArgumentException("The letters must either occupy the starting point or connect to existing pieces");
		}
		return ! (error || (boardWord.length() > 1 && (! legalPlacement)));
	}
}
