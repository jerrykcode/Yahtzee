package scoreCalculator;

public class SameKindCalculator implements ScoreCalculator {

	private int nSameKind; //Number of the values that must be the same
	
	public SameKindCalculator(int nSameKind) {
		this.nSameKind = nSameKind;
	}

	@Override
	public int calculateScore(int[] numbers) {
		int[] time = new int[7]; //Values in the 'numbers' array can only be 1~6		
		for (int i = 0; i < numbers.length; i++)
			time[numbers[i]]++;
		for (int i = 1; i <= 6; i++)
			if (time[i] >= nSameKind) { //If 'i' appeared more than 'nSameKind' times
				int sum = 0;
				for (int j = 0; j < numbers.length; j++)
					sum += numbers[j];
				return sum;
			}
		return 0; //If no value appeared more than 'nSameKind' times, then return 0
	}
	
}
