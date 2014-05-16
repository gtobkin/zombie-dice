package zombie_dice;

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
		dice = new int[COLOR_COUNT][STATE_COUNT];
		dice[COLOR_GREEN][STATE_CAN] = ZombieDice.DEFAULT_GREEN_DICE;
		dice[COLOR_YELLOW][STATE_CAN] = ZombieDice.DEFAULT_YELLOW_DICE;
		dice[COLOR_RED][STATE_CAN] = ZombieDice.DEFAULT_RED_DICE;
		// everything else defaults to 0
	}
	
	// default dice counts
	public TurnState(GameState _gameState, int _brains, int _shotguns, int[][] _dice) {
		gameState = _gameState;
		
		assert _brains >= 0;
		brains = _brains;
		
		assert _shotguns >= 0;
		assert _shotguns < 6;
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
		int handsize = _dice[COLOR_GREEN][STATE_HAND]
						+ _dice[COLOR_YELLOW][STATE_HAND]
						+ _dice[COLOR_RED][STATE_HAND];
		assert handsize >= 0;
		assert handsize < 4;
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
		assert _shotguns < 6;
		shotguns = _shotguns;
		
		assert _diceCounts.length == COLOR_COUNT;
		for (int color = 0; color != COLOR_COUNT; ++color) {
			assert _diceCounts[color] >= 0;
		}
		assert _diceCounts[COLOR_GREEN]
				+ _diceCounts[COLOR_YELLOW]
				+ _diceCounts[COLOR_RED] >= 5; // minimum dice count needed; two shotguns, three in hand
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
		int handsize = _dice[COLOR_GREEN][STATE_HAND]
						+ _dice[COLOR_YELLOW][STATE_HAND]
						+ _dice[COLOR_RED][STATE_HAND];
		assert handsize >= 0;
		assert handsize < 4;
		dice = _dice;
	}
	
	// ==================== VARIABLES ====================
	
	private GameState gameState;
	private int brains, shotguns;
	private int[] diceCounts;
	private int[][] dice;
	
}
