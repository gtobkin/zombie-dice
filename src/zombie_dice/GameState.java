package zombie_dice;

import java.util.Arrays;

public class GameState {
	
	// methods
	public GameState(int _players, int _currentPlayer, int[] _scores) {
		players = _players;
		currentPlayer = _currentPlayer;
		scores = _scores;
	}
	
	public GameState(int _players, int _currentPlayer, int _uniformScore) {
		players = _players;
		currentPlayer = _currentPlayer;
		scores = new int[players];
		Arrays.fill(scores, _uniformScore);
	}
	
	public int players() { return players; }
	public int currentPlayer() { return currentPlayer; }
	public int[] scores() { return scores; }
	
	public void setCurrentPlayer(int _currentPlayer) { currentPlayer = _currentPlayer; }
	
	// variables
	private int players, currentPlayer;
	private int[] scores;
}
