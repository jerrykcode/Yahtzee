package yahtzee;

import java.util.ArrayList;
import java.awt.Color;
import acm.graphics.*;


public class Dice extends GCompound {
	
	private static final double POINT_SIZE = 0.20;
	private static final double DICE_GRID_WIDTH = 0.25;
	
	private int pointNumber;
	private boolean isHighLight;
	private int size;
	private ArrayList<GObject> objects;
	private GRect diceRect;
	
    public Dice(int size){
    	pointNumber = 0;
    	isHighLight = false;
    	this.size = size;
    	objects = new ArrayList<GObject>();    	
    	drawDice();
	 	drawQuestion();
    }
    
    /* Add a question "?" on the dice. */
    public void drawQuestion() {
    	int x = (int)(getPixelSize(DICE_GRID_WIDTH));
    	int y = (int)(getPixelSize(DICE_GRID_WIDTH * 3));
    	GLabel question = new GLabel("?", x * 2, x * 2);
    	question.setColor(Color.BLUE);
    	question.setFont("SansSerif-bold-80");
    	add(question, x, y);
    	objects.add(question);
    }
    
    /* Set the point of the dice. */
    public void setPointNumber(int pointNumber) {
    	this.pointNumber = pointNumber;
    	switch(pointNumber) {
	 		case 1:drawPointOne(); break;	       
			case 2:drawPointTwo(); break;
			case 3:drawPointThree(); break;
			case 4:drawPointFour(); break;
			case 5:drawPointFive(); break;
			case 6:drawPointSix(); break;        
    	} 	   
    }
    
    /* Returns the point number. */
    public int getPointNumber() {
    	return pointNumber;
    }
    
    /* Removes all the objects on the dice. */
    public void removePoints() {
    	for (GObject object : objects) {
    		remove(object);
    	}
    	objects.clear();
    }
    
    /* Change color */
    public void changeColor() {
    	if (isHighLight) {
    		diceRect.setFillColor(Color.WHITE);    		
    	}
    	else {
    		diceRect.setFillColor(Color.YELLOW);
    	}
    	isHighLight = !isHighLight;
    }
    
    /* Set back to default color*/
    public void setDefaultColor() {
    	diceRect.setFillColor(Color.WHITE);
    	isHighLight = false;
    }
    
    private int getPixelSize(double d) {
		return (int)(size * d);
	}
    
    private void drawDice() {
    	diceRect = new GRect(size, size);
	 	diceRect.setFilled(true);
	 	diceRect.setFillColor(Color.WHITE);		
		add(diceRect,0,0);
    }
    
    private void drawPoint(int row, int col) {
    	GOval point = new GOval(getPixelSize(POINT_SIZE), getPixelSize(POINT_SIZE));
		point.setFilled(true);
		point.setFillColor(Color.BLUE);
		int x = (int)((col + 1) * getPixelSize(DICE_GRID_WIDTH) - getPixelSize(POINT_SIZE) / 2);
		int y = (int)((row + 1) * getPixelSize(DICE_GRID_WIDTH) - getPixelSize(POINT_SIZE) / 2);
		add(point, x, y);
		objects.add(point);
    }
    
    private void drawPointOne(){
	 	drawPoint(1, 1);
    }
        
    private void drawPointTwo(){
	 	drawPoint(0, 2);
	 	drawPoint(2, 0);
    }
    
    private void drawPointThree(){
    	drawPointTwo();
    	drawPoint(1, 1);    	
    }
    
    private void drawPointFour(){
		drawPoint(0, 0);
		drawPoint(0, 2);
		drawPoint(2, 0);
		drawPoint(2, 2);
    }
    
    private void drawPointFive(){
    	drawPointOne();
    	drawPointFour();    	
    }
    
    private void drawPointSix(){    	
		drawPointFour();
		drawPoint(1, 0);
		drawPoint(1, 2);
    }   
}
