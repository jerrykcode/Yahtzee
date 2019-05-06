package yahtzee;

import acm.program.*;

public class Yahtzee extends DialogProgram {

	public void run() {
		YahtzeeDisplay display = new YahtzeeDisplay(2, 900, 600);
		add(display.getCanvas());
		display.waitForPlayer();
	}
}
