package scoreCalculator;

public class StraightCalculator implements ScoreCalculator {

	private int nConsecutive; //Number of consecutive values
	private int score;
	
	public StraightCalculator(boolean isLarge) {
		if (isLarge) {
			nConsecutive = 5;
			score = 40;
		}
		else {
			nConsecutive = 4;
			score = 30;
		}
	}

	@Override
	public int calculateScore(int[] numbers) {
		boolean []hasAppeared = new boolean[7]; //Values in 'numbers' array can only be 1~6
		for (int value : numbers) {
			hasAppeared[value] = true;
		}
		for (int i = 1; i <= 6 - nConsecutive + 1; i++) {
			int j = i;
			for (; j <= 6; j++)
				if (!hasAppeared[j]) break;
			if (j - i >= nConsecutive) return score;
		}
		return 0;
	}

}
