package zombie_dice;

import java.util.Arrays;
import java.util.Random;

public class ZombieDice {
	
	// ==================== CONSTANTS ====================
	
	public static final int DEFAULT_GREEN_DICE = 6;
	public static final int DEFAULT_YELLOW_DICE = 4;
	public static final int DEFAULT_RED_DICE = 3;
	
	public static final int SHOTGUNS_TO_LOSE = 3;
	public static final int HAND_SIZE = 3;
	
	public static final int WINNING_SCORE = 13;
	
	// ==================== METHODS ====================
	
	public static void main(String[] args) {
		GameState game = new GameState(1, 0, 0);
		for (int i = 0; i < 20; ++i) {
			int[] scores = new int[100];
			for (int j = 0; j < 50000000; ++j) {
				TurnState turn = new TurnState(game);
				while (turn.shotguns() < 3) turn.roll();
				++scores[turn.brains()];
			}
			for (int j = 0; j != 100; ++j) {
				System.out.print(scores[j] + "\t");
			}
			System.out.print("\n");
		}
	}
	
	// ==================== VARIABLES ====================
	
	public static Random gen = new Random();

}
