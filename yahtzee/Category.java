package yahtzee;

import java.awt.Color;
import java.util.ArrayList;

import acm.graphics.*;

import scoreCalculator.*;

public class Category extends GCompound {

	private static final double BAR_WIDTH = 0.3;
	private static final double GRIDS_WIDTH = 0.7;
	private static final double LABEL_LEFT_WIDTH = 0.05;
	private static final double LABEL_BOTTOM_HEIGHT = 0.9;
	
	private int width;
	private int height;
	
	private String categoryString;
	private int nColumns;
	private GRect bar;
	private GLabel barLabel;
	private GRect[] grids;
	private GLabel[] scoreLabels;
	
	private ScoreCalculator calculator;
	
	public Category(String categoryString, int nColumns, int width, int height) {
		this.categoryString = categoryString;
		this.nColumns = nColumns;
		this.width = width;
		this.height = height;
		grids = new GRect[nColumns];
		scoreLabels = new GLabel[nColumns];
		for (int i = 0; i < nColumns; i++)
			scoreLabels[i] = null;
		bar = new GRect(getPixelWidth(BAR_WIDTH), height);
		bar.setFilled(true);
		bar.setFillColor(Color.GRAY);
		add(bar, 0, 0);
		barLabel = new GLabel(categoryString);
		add(barLabel, getPixelWidth(LABEL_LEFT_WIDTH), getPixelHeight(LABEL_BOTTOM_HEIGHT));
		for (int i = 0; i < nColumns; i++) {
			grids[i] = new GRect(getPixelWidth(GRIDS_WIDTH / nColumns), height);
			int x = getPixelWidth(BAR_WIDTH + i * GRIDS_WIDTH/nColumns);
			grids[i].setFilled(true);
			grids[i].setFillColor(Color.WHITE);
			add(grids[i], x, 0);
		}
		initScoreCalculator();
	}
	
	public ArrayList<GObject> getReponsiveObjects(int columnIndex) {
		ArrayList<GObject> reponsiveObjects = new ArrayList<GObject>();
		reponsiveObjects.add(bar);
		reponsiveObjects.add(barLabel);
		reponsiveObjects.add(grids[columnIndex]);
		return reponsiveObjects;
	}
	
	public int calculateScore(int [] number) {		
		return calculator.calculateScore(number);
	}
	
	public void addScoreLabel(int columnIndex, int score) {
		scoreLabels[columnIndex] = new GLabel("" + score);
		int x = getPixelWidth(BAR_WIDTH + columnIndex * GRIDS_WIDTH/nColumns + (columnIndex + 1) * LABEL_LEFT_WIDTH);
		int y = getPixelHeight(LABEL_BOTTOM_HEIGHT);
		add(scoreLabels[columnIndex], x, y);
	}
	
	public void removeScoreLabel(int columnIndex) {
		if (scoreLabels[columnIndex] != null) {
			remove(scoreLabels[columnIndex]);
			scoreLabels[columnIndex] = null;
		}
	}
	
	private void initScoreCalculator() {
		switch (categoryString) {
		case "Ones" : calculator = new OnesToSixesCalculator(1); break;
		case "Twos" : calculator = new OnesToSixesCalculator(2); break;
		case "Threes" : calculator = new OnesToSixesCalculator(3); break;
		case "Fours" : calculator = new OnesToSixesCalculator(4); break;
		case "Fives" : calculator = new OnesToSixesCalculator(5); break;
		case "Sixes" : calculator = new OnesToSixesCalculator(6); break;
		case "Three of a Kind" : calculator = new SameKindCalculator(3); break;
		case "Four of a Kind" : calculator = new SameKindCalculator(4); break;
		case "Full House[25]" : calculator = new FullHouseCalculator(); break;
		case "Small Straight[30]" : calculator = new StraightCalculator(false); break;
		case "Large Straight[40]" : calculator = new StraightCalculator(true); break;
		case "Yahtzee![50]" : calculator = new YahtzeeCalculator(); break;
		case "Chance" : calculator = new ChanceCalculator(); break;
		default: calculator = null; break;
		}
	}
	
	private int getPixelWidth(double d) {
		return (int)(width * d);
	}
	
	private int getPixelHeight(double d) {
		return (int)(height * d);
	}
}
