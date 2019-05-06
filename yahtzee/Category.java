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
	
	private int getPixelWidth(double d) {
		return (int)(width * d);
	}
	
	private int getPixelHeight(double d) {
		return (int)(height * d);
	}
}
