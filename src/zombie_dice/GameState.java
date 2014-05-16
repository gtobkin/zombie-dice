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
	
	// variables
	private int players, currentPlayer;
	private int[] scores;
}
