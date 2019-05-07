package scoreCalculator;

public class ChanceCalculator implements ScoreCalculator {

	public ChanceCalculator() {
		
	}
	
	@Override
	public int calculateScore(int[] numbers) {
		int sum = 0;
		for (int value : numbers)
			sum += value;
		return sum;
	}
}
