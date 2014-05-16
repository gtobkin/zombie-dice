package zombie_dice;

import java.util.Arrays;

public class GameState {
	
	// ==================== METHODS ====================
	
	public GameState(int _players, int _currentPlayer, int[] _scores) {
		assert _players > 0;
		players = _players;
		assert _currentPlayer < _players;
		currentPlayer = _currentPlayer;
		assert _scores.length == _players;
		if (_currentPlayer != 0) {
			for (int currentPlayer = _currentPlayer; currentPlayer != _players; ++currentPlayer) {
				assert _scores[currentPlayer] < ZombieDice.WINNING_SCORE;
			}
		}
		scores = _scores;
	}
	
	public GameState(int _players, int _currentPlayer, int _uniformScore) {
		assert _players > 0;
		players = _players;
		assert _currentPlayer < _players;
		currentPlayer = _currentPlayer;
		assert _uniformScore >= 0;
		if (_currentPlayer != 0) {
			assert _uniformScore < ZombieDice.WINNING_SCORE;
		}
		scores = new int[players];
		Arrays.fill(scores, _uniformScore);
	}
	
	public int players() { return players; }
	public int currentPlayer() { return currentPlayer; }
	public int[] scores() { return scores; }
	
	public void nextPlayer() { currentPlayer = ++currentPlayer % players; }
	
	public void print() {
		System.out.println("----- PRINTING GAMESTATE -----");
		System.out.println("PLAYERS:\t" + players);
		System.out.println("CURRENT_PLAYER:\t" + currentPlayer);
		System.out.println("SCORES:\t\t" + Arrays.toString(scores));
		System.out.println("----- FINISHED PRINTING GAMESTATE -----");
	}
	
	// ==================== VARIABLES ====================
	
	private int players, currentPlayer;
	private int[] scores;
	
}
