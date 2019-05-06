package yahtzee;

import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import acm.graphics.*;
import acm.util.RandomGenerator;

public class YahtzeeDisplay implements MouseListener, ActionListener {
	
	//Board
	private static final double UPPER_BORDER_HEIGHT = 0.03; 
	private static final double BOTTOM_BORDER_HEIGHT = 0.03;
	private static final double LEFT_BORDER_WIDTH = 0.04;
	private static final double RIGHT_BORDER_WIDTH = 0.04;
	private static final double DICE_BAR_WIDTH = 0.18;
	private static final double DICE_WIDTH = 0.108; //width
	private static final double DICE_INTERVAL_HEIGHT = 0.01;
	private static final double CATEGORY_BAR_WIDTH = 0.7;
	private static final double CATEGORY_BAR_HEIGHT = 0.84;
	private static final double SCORE_BAR_WIDTH = 0.4;
	private static final double BUTTON_BAR_HEIGHT = 0.1;
	
	private static final int N_CATEGORIES = 17;
	private static final int N_DICES = 5;
	
	private static final String[] categoryStrings = {"Ones", "Twos", "Threes", "Fours", "Fives", "Sixes", 
													 "Upper Score", "Upper Bonus[35]", "Three of a Kind", 
													 "Four of a Kind", "Full House[25]", "Small Straight[30]",
													 "Large Straight[40]", "Yahtzee![50]", "Chance", 
													 "Lower Score", "TOTAL"};
	
	//category index
	private static final int UPPER_SCORE_INDEX = 6;
	private static final int UPPER_BONUS_INDEX = 7;
	private static final int LOWER_SCORE_INDEX = 15;
	private static final int TOTAL_INDEX = 16;
	
	private RandomGenerator random = new RandomGenerator();
	
	//Canvas width & height
	private int canvasWidth;
	private int canvasHeight;
	
	//Game information
	private GCanvas canvas;
	private int nPlayers;	
	private int playerTurn;
	private int rollTime;
	private boolean[] isSelected;
	private int[] diceNumber;
	private int[] upperScore;
	private int[] lowerScore;
	private int[] totalScore;
	private boolean[] hasUpperBonus;
	private boolean[][] categoryUsed;
	
	//Button
	private JButton rollDiceButton;
	
	//Dice array
	private Dice[] dices;
	
	//Category array
	private Category[] categories;
	
	public YahtzeeDisplay (int nPlayers, int canvasWidth, int canvasHeight) {
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.nPlayers = nPlayers;	
		playerTurn = 0;
		rollTime = 0;
		isSelected = new boolean[N_DICES];
		diceNumber = new int[N_DICES];
		for (int i = 0; i < N_DICES; i++) {
			isSelected[i] = true;
			diceNumber[i] = 0;
		}
		upperScore = new int[nPlayers];
		lowerScore = new int[nPlayers];
		totalScore = new int[nPlayers];
		hasUpperBonus = new boolean[nPlayers];
		categoryUsed = new boolean[nPlayers][N_CATEGORIES];
		for (int i = 0; i < nPlayers; i++) {
			upperScore[i] = lowerScore[i] = totalScore[i] = 0;
			hasUpperBonus[i] = false;
			for (int j = 0; j < N_CATEGORIES; j++) {
				categoryUsed[i][j] = false;
			}
		}
		canvas = new GCanvas();
		canvas.setSize(canvasWidth, canvasHeight);
		init();
	}
	
	public GCanvas getCanvas() {
		return canvas;
	}
	
	public void waitForPlayer() {
		canvas.addMouseListener(this);
		rollDiceButton.addActionListener(this);
	}
	
	private int getPixelWidth(double d) {
		return (int)(canvasWidth * d);
	}
	
	private int getPixelHeight(double d) {
		return (int)(canvasHeight * d);
	}
	
	private void init() {
		canvas.setBackground(Color.GREEN);
		//Roll Dice button
		initRollDiceButton();
		//Dice
		initDice();
		//Category
		initCategory();
	}
	
	private void initRollDiceButton() {
		rollDiceButton = new JButton("Roll Dice");
		rollDiceButton.setSize(getPixelWidth(DICE_BAR_WIDTH), getPixelHeight(BUTTON_BAR_HEIGHT));
		rollDiceButton.setBackground(Color.CYAN);		
		canvas.add(rollDiceButton, getPixelWidth(LEFT_BORDER_WIDTH), getPixelHeight(UPPER_BORDER_HEIGHT));
	}

	private void initDice() {
		dices = new Dice[N_DICES];		
		for (int i = 0; i < N_DICES; i++) {
			dices[i] = new Dice(getPixelWidth(DICE_WIDTH));
			int x = getPixelWidth(LEFT_BORDER_WIDTH) + (getPixelWidth(DICE_BAR_WIDTH) - getPixelWidth(DICE_WIDTH)) / 2;
			int y = getPixelHeight(UPPER_BORDER_HEIGHT) + getPixelHeight(BUTTON_BAR_HEIGHT)
				+ (i + 1) * getPixelHeight(DICE_INTERVAL_HEIGHT) + i * getPixelWidth(DICE_WIDTH);
			canvas.add(dices[i], x, y);
		}
	}
	
	private void initCategory() {
		categories = new Category[N_CATEGORIES];
		int categoryWidth = getPixelWidth(CATEGORY_BAR_WIDTH);
		int categoryHeight = getPixelHeight(CATEGORY_BAR_HEIGHT / N_CATEGORIES);
		int x = getPixelWidth(LEFT_BORDER_WIDTH * 2 + DICE_BAR_WIDTH);
		for (int i = 0; i < N_CATEGORIES; i++) {
			categories[i] = new Category(categoryStrings[i], nPlayers, categoryWidth, categoryHeight);
			int y = getPixelHeight(UPPER_BORDER_HEIGHT + BUTTON_BAR_HEIGHT + i * CATEGORY_BAR_HEIGHT / N_CATEGORIES);
			canvas.add(categories[i], x, y);	
		}
	}
	
	public void actionPerformed(ActionEvent e) {		
		if (rollTime > 2) return;
		for (int i = 0; i < N_DICES; i++) {
			if (isSelected[i]) {
				diceNumber[i] = random.nextInt(1, 6);					
				dices[i].removePoints();
				dices[i].setPointNumber(diceNumber[i]);
				dices[i].setDefaultColor();
			}
		}
		for (int i = 0; i < N_DICES; i++)
			isSelected[i] = false;
		if (rollTime == 0) {
			rollDiceButton.setText("Roll again");
		}
		else if (rollTime == 2) {
			rollDiceButton.setEnabled(false);
		}
		rollTime++;		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (clickDice(canvas.getElementAt(e.getX(), e.getY()))) return;
		if (clickCategory(canvas.getElementAt(e.getX(), e.getY()))) return;
	}
	
	private boolean clickDice(GObject object) {		
		if (rollTime == 1 || rollTime == 2)
			for (int i = 0; i < N_DICES; i++)
				if (dices[i] == object) {
					isSelected[i] = !isSelected[i];
					dices[i].changeColor();			
				}	
		return false;
	}

	private boolean clickCategory(GObject object) {
		if (rollTime == 3) {
			int categoryIndex = 0;
			boolean flag = false;
			for (; categoryIndex < N_CATEGORIES; categoryIndex++)
				//for (GObject reponsiveObject : categories[categoryIndex].getReponsiveObjects(playerTurn))
				if (categories[categoryIndex] == object) {
					flag = true;
					break;
				}
			if (!flag) return false; //If no category was clicked, return false
			if (categoryIndex == UPPER_SCORE_INDEX
					|| categoryIndex == UPPER_BONUS_INDEX
					|| categoryIndex == LOWER_SCORE_INDEX
					|| categoryIndex == TOTAL_INDEX) return false;
			if (categoryUsed[playerTurn][categoryIndex]) return false;
			categoryUsed[playerTurn][categoryIndex] = true;
			int score = categories[categoryIndex].calculateScore(diceNumber);			
			totalScore[playerTurn] += score;
			categories[categoryIndex].addScoreLabel(playerTurn, score); //Add score label			
			if (categoryIndex < UPPER_SCORE_INDEX) { //upper score
				upperScore[playerTurn] += score;
				if (upperScore[playerTurn] >= 63 && !hasUpperBonus[playerTurn]) { //Bonus
					totalScore[playerTurn] += 35;
					hasUpperBonus[playerTurn] = true;
				}
				//Update upper score label
				categories[UPPER_SCORE_INDEX].removeScoreLabel(playerTurn);
				categories[UPPER_SCORE_INDEX].addScoreLabel(playerTurn, upperScore[playerTurn]);
			}
			else { //lower score
				lowerScore[playerTurn] += score;
				//Update lower score label
				categories[LOWER_SCORE_INDEX].removeScoreLabel(playerTurn);
				categories[LOWER_SCORE_INDEX].addScoreLabel(playerTurn, lowerScore[playerTurn]);
			}
			//Update total score label
			categories[TOTAL_INDEX].removeScoreLabel(playerTurn);
			categories[TOTAL_INDEX].addScoreLabel(playerTurn, totalScore[playerTurn]);			
			//Update for the next player
			//
			playerTurn = (playerTurn + 1) % nPlayers; //Next player
			rollTime = 0;
			rollDiceButton.setEnabled(true);
			rollDiceButton.setText("Roll Dice");		
			for (int i = 0; i < N_DICES; i++) {	
				isSelected[i] = true;
				diceNumber[i] = 0;
				dices[i].removePoints();
				dices[i].drawQuestion();
			}
			return true;
		}
		else return false;
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
