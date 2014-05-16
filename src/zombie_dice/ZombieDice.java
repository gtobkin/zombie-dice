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
	
	public static final int WINNING_SCORE = 5;
	
	// ==================== METHODS ====================
	
	public static void main(String[] args) {
		//computeBrainDistribution(10, 1000);
		Strategy strat = new Strategy(3);
		strat.learnBaseCases();
	}
	
	public static void computeBrainDistribution(int samples, int turnsPerSample) {
		final int MAX_BRAINS = 50;
		GameState game = new GameState(1, 0, 0);
		for (int i = 0; i < samples; ++i) {
			int[] scores = new int[MAX_BRAINS + 1];
			for (int j = 0; j < turnsPerSample; ++j) {
				TurnState turn = new TurnState(game);
				while (turn.shotguns() < 3) turn.roll();
				++scores[turn.brains()];
			}
			for (int j = 0; j != MAX_BRAINS + 1; ++j) {
				System.out.print(scores[j] + "\t");
			}
			System.out.print("\n");
		}
	}
	
	// ==================== VARIABLES ====================
	
	public static Random gen = new Random();

}
