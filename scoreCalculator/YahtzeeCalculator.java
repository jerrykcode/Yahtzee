package scoreCalculator;

public class YahtzeeCalculator implements ScoreCalculator {

	public YahtzeeCalculator() {
		
	}
	
	@Override
	public int calculateScore(int[] numbers) {
		for (int i = 1; i < numbers.length; i++)
			if (numbers[i] != numbers[0]) return 0;
		return 50;
	}

}
