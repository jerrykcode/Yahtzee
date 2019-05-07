package scoreCalculator;

public class OnesToSixesCalculator implements ScoreCalculator {

	private int value;
	
	public OnesToSixesCalculator(int value) {
		this.value = value;
	}
	
	@Override
	public int calculateScore(int[] numbers) {		
		int sum = 0;
		for (int i = 0; i < numbers.length; i++) {
			if (numbers[i] == value) sum += value;
		}
		return sum;
	}

}
