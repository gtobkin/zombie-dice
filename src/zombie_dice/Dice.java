package zombie_dice;

public class Dice {

	// ==================== CONSTANTS ====================
	
	public static int SIDE_BRAIN = 0;
	public static int SIDE_RUNNER = 1;
	public static int SIDE_SHOTGUN = 2;
	
	// [color][side]
	public static final double[][] PROB = {
			{ 0.5, 			1.0 / 3.0, 	1.0 / 6.0 },
			{ 1.0 / 3.0,	1.0 / 3.0,	1.0 / 3.0 },
			{ 1.0 / 6.0,	1.0 / 3.0,	0.5	      }
	};
	
}
