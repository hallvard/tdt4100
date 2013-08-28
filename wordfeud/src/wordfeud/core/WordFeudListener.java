package wordfeud.core;

public interface WordFeudListener {
	public void playerChanged(Player player);
	public void piecesChanged(int pos);
}
