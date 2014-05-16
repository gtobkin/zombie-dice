package zombie_dice;

import java.util.Arrays;

public class TurnState {
	
	// ==================== CONSTANTS ====================
	
	public static final int COLOR_COUNT = 3;
	public static final int STATE_COUNT = 4;

	public static final int COLOR_GREEN = 0;
	public static final int COLOR_YELLOW = 1;
	public static final int COLOR_RED = 2;
	
	public static final int STATE_BRAIN = 0;
	public static final int STATE_SHOTGUN = 1;
	public static final int STATE_HAND = 2;
	public static final int STATE_CAN = 3;
	
	// ==================== METHODS ====================
	
	public TurnState(GameState _gameState) {
		gameState = _gameState;
		brains = 0;
		shotguns = 0;
		handsize = 0;
		cansize = ZombieDice.DEFAULT_GREEN_DICE
				+ ZombieDice.DEFAULT_YELLOW_DICE
				+ ZombieDice.DEFAULT_RED_DICE;
		dice = new int[COLOR_COUNT][STATE_COUNT];
		dice[COLOR_GREEN][STATE_CAN] = ZombieDice.DEFAULT_GREEN_DICE;
		dice[COLOR_YELLOW][STATE_CAN] = ZombieDice.DEFAULT_YELLOW_DICE;
		dice[COLOR_RED][STATE_CAN] = ZombieDice.DEFAULT_RED_DICE;
		diceCounts = new int[] { ZombieDice.DEFAULT_GREEN_DICE,
								 ZombieDice.DEFAULT_YELLOW_DICE,
								 ZombieDice.DEFAULT_RED_DICE };
		// everything else defaults to 0
	}
	
	// default dice counts
	public TurnState(GameState _gameState, int _brains, int _shotguns, int[][] _dice) {
		gameState = _gameState;
		
		assert _brains >= 0;
		brains = _brains;
		
		assert _shotguns >= 0;
		assert _shotguns < ZombieDice.SHOTGUNS_TO_LOSE + ZombieDice.HAND_SIZE;
		shotguns = _shotguns;
		
		assert _dice[COLOR_GREEN][STATE_BRAIN]
				+ _dice[COLOR_GREEN][STATE_SHOTGUN]
				+ _dice[COLOR_GREEN][STATE_HAND]
				+ _dice[COLOR_GREEN][STATE_CAN] == ZombieDice.DEFAULT_GREEN_DICE;
		assert _dice[COLOR_YELLOW][STATE_BRAIN]
				+ _dice[COLOR_YELLOW][STATE_SHOTGUN]
				+ _dice[COLOR_YELLOW][STATE_HAND]
				+ _dice[COLOR_YELLOW][STATE_CAN] == ZombieDice.DEFAULT_YELLOW_DICE;
		assert _dice[COLOR_RED][STATE_BRAIN]
				+ _dice[COLOR_RED][STATE_SHOTGUN]
				+ _dice[COLOR_RED][STATE_HAND]
				+ _dice[COLOR_RED][STATE_CAN] == ZombieDice.DEFAULT_RED_DICE;
		assert _dice[COLOR_GREEN][STATE_SHOTGUN]
				+ _dice[COLOR_YELLOW][STATE_SHOTGUN]
				+ _dice[COLOR_RED][STATE_SHOTGUN] == _shotguns;
		handsize = 0;
		cansize = 0;
		for (int color = 0; color != COLOR_COUNT; ++color) {
			handsize += dice[color][STATE_HAND];
			cansize += dice[color][STATE_CAN];
		}
		assert handsize >= 0;
		assert handsize <= ZombieDice.HAND_SIZE;
		diceCounts = new int[] { ZombieDice.DEFAULT_GREEN_DICE,
								 ZombieDice.DEFAULT_YELLOW_DICE,
								 ZombieDice.DEFAULT_RED_DICE };
		dice = _dice;
	}
	
	// custom dice counts
	public TurnState(GameState _gameState, int _brains, int _shotguns, int[][] _dice, int[] _diceCounts) {
		gameState = _gameState;
		
		assert _brains >= 0;
		brains = _brains;
		
		assert _shotguns >= 0;
		assert _shotguns < ZombieDice.SHOTGUNS_TO_LOSE + ZombieDice.HAND_SIZE;
		shotguns = _shotguns;
		
		assert _diceCounts.length == COLOR_COUNT;
		for (int color = 0; color != COLOR_COUNT; ++color) {
			assert _diceCounts[color] >= 0;
		}
		assert _diceCounts[COLOR_GREEN]
				+ _diceCounts[COLOR_YELLOW]
				+ _diceCounts[COLOR_RED] >= ZombieDice.SHOTGUNS_TO_LOSE + ZombieDice.HAND_SIZE;
		diceCounts = _diceCounts;
		
		assert _dice[COLOR_GREEN][STATE_BRAIN]
				+ _dice[COLOR_GREEN][STATE_SHOTGUN]
				+ _dice[COLOR_GREEN][STATE_HAND]
				+ _dice[COLOR_GREEN][STATE_CAN] == diceCounts[COLOR_GREEN];
		assert _dice[COLOR_YELLOW][STATE_BRAIN]
				+ _dice[COLOR_YELLOW][STATE_SHOTGUN]
				+ _dice[COLOR_YELLOW][STATE_HAND]
				+ _dice[COLOR_YELLOW][STATE_CAN] == diceCounts[COLOR_YELLOW];
		assert _dice[COLOR_RED][STATE_BRAIN]
				+ _dice[COLOR_RED][STATE_SHOTGUN]
				+ _dice[COLOR_RED][STATE_HAND]
				+ _dice[COLOR_RED][STATE_CAN] == diceCounts[COLOR_RED];
		assert _dice[COLOR_GREEN][STATE_SHOTGUN]
				+ _dice[COLOR_YELLOW][STATE_SHOTGUN]
				+ _dice[COLOR_RED][STATE_SHOTGUN] == _shotguns;
		
		handsize = 0;
		cansize = 0;
		for (int color = 0; color != COLOR_COUNT; ++color) {
			handsize += dice[color][STATE_HAND];
			cansize += dice[color][STATE_CAN];
		}
		assert handsize >= 0;
		assert handsize <= ZombieDice.HAND_SIZE;
		dice = _dice;
	}
	
	// roll one hand of dice
	public void roll() {
		if (shouldRefillCan()) refillCan();
		refillHand();
		for (int color = 0; color != COLOR_COUNT; ++color) {
			int rolls = dice[color][STATE_HAND]; // saving current value, may change mid-rolls
			for (int roll = 0; roll != rolls; ++roll) {
				rollColor(color);
			}
		}
	}
	
	// rules say to refill can if <3 dice in can, regardless of hand size
	private boolean shouldRefillCan() {
		return cansize < ZombieDice.HAND_SIZE;
	}
	
	private void refillCan() {
		for (int color = 0; color != COLOR_COUNT; ++color) {
			dice[color][STATE_CAN] += dice[color][STATE_BRAIN];
			cansize += dice[color][STATE_BRAIN];
			dice[color][STATE_BRAIN] = 0;
		}
	}
	
	private void refillHand() {
		while (handsize < ZombieDice.HAND_SIZE) {
			drawOneDie();
		}
	}
	
	private void drawOneDie() {
		assert cansize > 0;
		assert handsize < ZombieDice.HAND_SIZE;
		int rand = ZombieDice.gen.nextInt(cansize);
		int color = -1;
		if (rand < dice[COLOR_GREEN][STATE_CAN]) {
			color = COLOR_GREEN;
		} else if (rand < dice[COLOR_GREEN][STATE_CAN]
				+ dice[COLOR_YELLOW][STATE_CAN]) {
			color = COLOR_YELLOW;
		} else {
			color = COLOR_RED;
		}
		assert color != -1;
		--cansize;
		--dice[color][STATE_CAN];
		++handsize;
		++dice[color][STATE_HAND];
	}
	
	private void rollColor(int color) {
		double outcome = ZombieDice.gen.nextDouble();
		if (outcome < Dice.PROB[color][Dice.SIDE_BRAIN]) {
			++brains;
			++dice[color][STATE_BRAIN];
			--handsize;
			--dice[color][STATE_HAND];
		} else if (outcome < Dice.PROB[color][Dice.SIDE_BRAIN]
				+ Dice.PROB[color][Dice.SIDE_RUNNER]) {
			// rolled a runner; do nothing
		} else {
			// rolled a shotgun
			++shotguns;
			++dice[color][STATE_SHOTGUN];
			--handsize;
			--dice[color][STATE_HAND];
		}
	}
	
	public void print() {
		System.out.println("========== PRINTING TURNSTATE ==========");
		System.out.println("BRAINS:   " + brains);
		System.out.println("SHOTGUNS: " + shotguns);
		System.out.println("HANDSIZE: " + handsize);
		System.out.println("CANSIZE:  " + cansize);
		System.out.println("DICE:\t\tGREEN\tYELLOW\tRED");
		System.out.println("BRAINS:\t\t" + dice[COLOR_GREEN][STATE_BRAIN]
				+ "\t" + dice[COLOR_YELLOW][STATE_BRAIN]
				+ "\t" + dice[COLOR_RED][STATE_BRAIN]);
		System.out.println("SHOTGUNS:\t" + dice[COLOR_GREEN][STATE_SHOTGUN]
				+ "\t" + dice[COLOR_YELLOW][STATE_SHOTGUN]
				+ "\t" + dice[COLOR_RED][STATE_SHOTGUN]);
		System.out.println("HAND:\t\t" + dice[COLOR_GREEN][STATE_HAND]
				+ "\t" + dice[COLOR_YELLOW][STATE_HAND]
				+ "\t" + dice[COLOR_RED][STATE_HAND]);
		System.out.println("CAN:\t\t" + dice[COLOR_GREEN][STATE_CAN]
				+ "\t" + dice[COLOR_YELLOW][STATE_CAN]
				+ "\t" + dice[COLOR_RED][STATE_CAN]);
		gameState.print();
		System.out.println("========== FINISHED PRINTING TURNSTATE ==========");
	}
	
	public int brains() { return brains; }
	
	public int shotguns() { return shotguns; }
	
	// ==================== VARIABLES ====================
	
	private GameState gameState;
	private int brains, shotguns, cansize, handsize;
	private int[] diceCounts;
	private int[][] dice;
	
}
