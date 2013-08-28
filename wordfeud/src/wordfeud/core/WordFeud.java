package wordfeud.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import wordfeud.io.OrdbankWords;

public class WordFeud {

	private Words words;
	private Letters letters;
	private MutableBoard sharedBoard;

	private List<Piece> bag = new ArrayList<Piece>();
	private List<Player> players;
	private int localPlayer = 0, currentPlayer;
	private int roundNum = 0;

	private Shuffler<Piece> piecesShuffler = new RandomShuffler<Piece>();
	private Shuffler<Player> playerShuffler = new RandomShuffler<Player>();

	public WordFeud(List<Player> players) {
		words = OrdbankWords.create();
		letters = LanguageLetters.load("nb");
		sharedBoard = CellListBoard.load("default");
		for (Character letter : letters) {
			addPieces(bag, letter, letters.getCount(letter));
		}
		addPieces(bag, Piece.NO_LETTER, 2);
		piecesShuffler.shuffle(bag, 10);
		this.players = new ArrayList<Player>(players);
		playerShuffler.shuffle(this.players, 1);
		for (Player player : players) {
			player.newGame(sharedBoard);
			List<Piece> initialPieces = new ArrayList<Piece>();
			movePieces(bag, initialPieces, 7);
			player.newRound(players.indexOf(player) + 1, initialPieces);
		}
		roundNum = 1;
	}

	public WordFeud(int playerCount) {
		this(createPlayers(playerCount));
	}

	public void setPiecesShuffler(Shuffler<Piece> shuffler) {
		this.piecesShuffler = shuffler;
	}
	
	private static List<Player> createPlayers(int playerCount) {
		List<Player> players = new ArrayList<Player>(playerCount);
		for (int i = 1; i <= playerCount; i++) {
			Player player = new Player("Player " + i);
			players.add(player);
		}
		return players;
	}

	private void addPieces(List<Piece> pieces, char letter, int count) {
		for (int i = 1; i <= count; i++) {
			pieces.add(letter == Piece.NO_LETTER ? new WildcardPiece(i) : new Piece(letter, i));
		}
	}

	private void movePieces(List<Piece> source, List<Piece> target, int count) {
		while (count-- != 0) {
			if (source.isEmpty()) {
				break;
			}
			Piece piece = source.remove(source.size() - 1);
			int pos = target.indexOf(null);
			if (pos >= 0) {
				target.set(pos, piece);
			} else {
				target.add(piece);
			}
		}
	}
	
	public Words getWords() {
		return words;
	}
	
	public Letters getLetters() {
		return letters;
	}
	
	public Board getBoard() {
		return sharedBoard;
	}
	
	//
	
	public Player getPlayer(int i) {
		return players.get(currentPlayer);
	}
	public int getCurrentPlayerNum() {
		return currentPlayer;
	}
	
	public int getRoundNum() {
		return roundNum;
	}
	
	public boolean isPlacedPiece(Piece piece) {
		return piece.getRoundPlayed() == getRoundNum();
	}

	// move within pieces
	public void movePiece(int sourcePos, int targetPos) {
		getLocalPlayer().movePiece(sourcePos, targetPos);
	}

	// move from pieces to board
	public void placePiece(int sourcePos, int targetX, int targetY) {
		placePiece(getLocalPlayer(), sourcePos, targetX, targetY);
	}

	private void placePiece(Player player, int sourcePos, int targetX, int targetY) {
		if (player.getPlayerBoard().getPiece(targetX, targetY) != null) {
			throw new IllegalArgumentException("The target location " + targetX + "," + targetY + " is occupied");
		}
		player.placePiece(sourcePos, targetX, targetY);
	}
	
	// move within board
	public void movePiece(int sourceX, int sourceY, int targetX, int targetY) {
		movePiece(getLocalPlayer(), sourceX, sourceY, targetX, targetY);
	}

	private void movePiece(Player player, int sourceX, int sourceY, int targetX, int targetY) {
		Piece piece = player.getPlayerBoard().getPiece(sourceX, sourceY);
		if (piece == null) {
			throw new IllegalArgumentException("There is no piece at " + sourceX + "," + sourceY + " cannot be moved");
		}
		if (sharedBoard.getPiece(sourceX, sourceY) != null) {
			throw new IllegalArgumentException("The piece " + piece + " at " + sourceX + "," + sourceY + " cannot be moved");
		}
		if (player.getPlayerBoard().getPiece(targetX, targetY) != null) {
			throw new IllegalArgumentException("The target location " + targetX + "," + targetY + " is occupied");
		}
		player.movePiece(sourceX, sourceY, targetX, targetY);
	}
	
	// move from board back to pieces
	public void placePieceBack(int sourceX, int sourceY, int targetPos) {
		placePieceBack(getLocalPlayer(), sourceX, sourceY, targetPos);
	}

	private void placePieceBack(Player player, int sourceX, int sourceY, int targetPos) {
		BoardPiece boardPiece = player.getPlayerBoard().getPlayerRound().getBoardPiece(sourceX, sourceY);
		if (boardPiece == null) {
			throw new IllegalArgumentException("The piece at " + sourceX + "," + sourceY + " cannot be moved");
		}
		player.placePieceBack(sourceX, sourceY, targetPos);
	}

	private Player getPlayer(String id) {
		for (Player player : players) {
			if (id.equals(player.getId())) {
				return player;
			}
		}
		return null;
	}

	private Player getLocalPlayer() {
		return players.get(currentPlayer);
	}
	
	private List<Piece> getLocalPieces() {
		return players.get(localPlayer).getPieces();
	}
	
	public Player getCurrentPlayer() {
		return players.get(getCurrentPlayerNum());
	}
	
	public void playPieces() {
		if (localPlayer != currentPlayer) {
			throw new IllegalStateException("It is not the local player's turn");
		}
		playPieces(getLocalPlayer());
	}

	public void playPieces(String playerId, Map<String, BoardLocation> pieceLocations) {
		Player player = getPlayer(playerId);
		if (player == null) {
			throw new IllegalArgumentException("There is no player with the ID " + playerId);
		} else if (player != players.get(currentPlayer)) {
			throw new IllegalArgumentException("The player with ID " + playerId + " does not have the turn");
		}
		List<BoardPiece> boardPieces = new ArrayList<BoardPiece>();
		List<Piece> playerPieces = player.getPieces();
		outer: for (String pieceId : pieceLocations.keySet()) {
			for (Piece piece : playerPieces) {
				if (pieceId.equals(piece.getId())) {
					boardPieces.add(new BoardPiece(getRoundNum(), piece, pieceLocations.get(pieceId)));
					continue outer;
				}
			}
			throw new IllegalArgumentException("Player " + playerId + " does not have the piece " + pieceId);
		}
		for (BoardPiece boardPiece : boardPieces) {
			BoardLocation location = boardPiece.getLocation();
			if (sharedBoard.getPiece(location.x, location.y) != null) {
				placePieceBack(player, location.x, location.y, -1);
			}
			placePiece(player, playerPieces.indexOf(boardPiece.getPiece()), location.x, location.y);
		}
		playPieces(player);
	}
	
	public void playPieces(Player player) {
		player.playPieces();
	}
	
	private void nextPlayer() {
		currentPlayer = (currentPlayer + 1) % players.size();
		roundNum++;
		firePlayerChanged(players.get(currentPlayer));
	}

	public void placePiecesBack() {
		getLocalPlayer().placePiecesBack();
	}

	public void shufflePieces() {
		piecesShuffler.shuffle(getLocalPlayer().getPieces(), 10);
	}
	
	public void pass() {
		nextPlayer();
	}

	//
	
	private List<WordFeudListener> wordFeudListeners = new ArrayList<WordFeudListener>();
	
	public void addWordFeudListener(WordFeudListener listener) {
		wordFeudListeners.add(listener);
	}
	
	public void removeWordFeudListener(WordFeudListener listener) {
		wordFeudListeners.remove(listener);
	}
	
	protected void firePlayerChanged(Player player) {
		for (WordFeudListener listener : wordFeudListeners) {
			listener.playerChanged(player);
		}
	}

	protected void firePiecesChanged(int pos) {
		for (WordFeudListener listener : wordFeudListeners) {
			listener.piecesChanged(pos);
		}
	}

	protected void firePiecesChanged(int startPos, int endPos) {
		if (endPos < 0) {
			endPos = getLocalPieces().size() + endPos;
		}
		int dx = (int) Math.signum(endPos - startPos);
		while (true) {
			firePiecesChanged(startPos);
			if (startPos == endPos) {
				break;
			}
			startPos += dx;
		}
	}
}
