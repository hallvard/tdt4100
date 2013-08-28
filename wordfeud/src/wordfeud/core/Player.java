package wordfeud.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player implements Points {

	private String id, name;
	
	public Player(String id) {
		this.id = id;
		this.name = id;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private List<PlayerRound> rounds = new ArrayList<PlayerRound>();
	private List<Piece> pieces = new ArrayList<Piece>(7);
	private PlayerBoard playerBoard;
	
	public void newGame(MutableBoard board) {
		playerBoard = new PlayerBoard(board);
	}
	
	public void newRound(int roundNum, List<Piece> newPieces) {
		PlayerRound lastRound = playerBoard.newRound(roundNum);
		rounds.add(lastRound);
		pieces.addAll(newPieces);
	}

	List<Piece> getPieces() {
		return pieces;
	}

	PlayerBoard getPlayerBoard() {
		return playerBoard;
	}

	// move from pieces to board
	public void placePiece(int sourcePos, int targetX, int targetY) {
		if (playerBoard.getPiece(targetX, targetY) != null) {
			throw new IllegalArgumentException("The target location " + targetX + "," + targetY + " is occupied");
		}
		Piece piece = clearPiece(sourcePos);
		playerBoard.placePiece(piece, targetX, targetY);
	}

	private Piece clearPiece(int pos) {
		Piece piece = pieces.get(pos);
		pieces.set(pos, null);
//		firePiecesChanged(pos);
		return piece;
	}

	// move within board
	public void movePiece(int sourceX, int sourceY, int targetX, int targetY) {
		Piece piece = playerBoard.getPiece(sourceX, sourceY);
		playerBoard.placePiece(piece, targetX, targetY);
	}

	public Piece getPiece(int i) {
		return i < pieces.size() ? pieces.get(i) : null;
	}

	private void setPiece(Piece piece, int pos) {
		if (pos < 0) {
			pos = pieces.indexOf(null);
			if (pos < 0) {
				throw new IllegalArgumentException("Cannot make room for piece");
			}
		}
		if (getPiece(pos) != null) {
			int emptyPos = pieces.indexOf(null);
			if (emptyPos < 0) {
				throw new IllegalArgumentException("Cannot make room for piece at " + pos);
			}
			pieces.remove(emptyPos);
			pieces.add(pos, null);
//			firePiecesChanged(Math.min(pos, emptyPos), -1);
		}
		pieces.set(pos, piece);
//		firePiecesChanged(pos);
	}

	public void movePiece(int sourcePos, int targetPos) {
		Piece piece = getPiece(sourcePos);
		pieces.set(sourcePos, null);
		setPiece(piece, targetPos);
	}

	// move from board back to pieces
	public void placePieceBack(int sourceX, int sourceY, int targetPos) {
		BoardPiece boardPiece = playerBoard.getPlayerRound().getBoardPiece(sourceX, sourceY);
		if (boardPiece == null) {
			throw new IllegalArgumentException("The piece at " + sourceX + "," + sourceY + " cannot be moved");
		}
		Piece piece = boardPiece.getPiece();
		playerBoard.removePiece(piece);
		setPiece(piece, targetPos);
		piece.setRoundPlayed(-1);
	}

	public void placePiecesBack() {
		for (BoardPiece boardPiece : getPlayerBoard().getPlayerRound()) {
			BoardLocation location = boardPiece.getLocation();
			placePieceBack(location.x, location.y, getPieces().indexOf(null));
		}
	}

	// play placed pieces
	public PlayerRound playPieces() {
		try {
			PlayerRound playerRound = getPlayerBoard().getPlayerRound();
			if (playerRound.computeBoardWords(false)) {
				return getPlayerBoard().newRound(playerRound.getRoundNum() + 1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}
	
	//
	
	@Override
	public int getPoints(WordFeud wordFeud) {
		int points = 0;
		for (PlayerRound round : rounds) {
			points += round.getPoints(wordFeud);
		}
		return points;
	}

	public Iterator<Piece> getPiecesIterator() {
		return getPieces().iterator();
	}
}
