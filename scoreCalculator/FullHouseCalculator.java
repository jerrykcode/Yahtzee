package scoreCalculator;

public class FullHouseCalculator implements ScoreCalculator {

	public FullHouseCalculator() {
		
	}

	@Override
	public int calculateScore(int[] numbers) {
		int [] time = new int[7]; //The values in 'numbers' array can only be 1~6
		for (int value : numbers) {
			time[value]++;
		}
		boolean has3Same = false;
		boolean has2Same = false;
		for (int i = 1; i <= 6; i++) {
			if (time[i] == 3) has3Same = true;
			else if (time[i] == 2) has2Same = true;
		}
		if (has3Same && has2Same) return 25;
		else return 0;
	}

}
