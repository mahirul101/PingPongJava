package ppPackage;

import static ppPackage.ppSimParams.*;


import java.awt.Color;

import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

/**
 * This class creates a paddle instance and exports methods
 * to interact with paddle.
 * Part of this code was taken from Assignment 3 document by Frank P. Ferrie and from Katrina Poulin's tutorial.
 * @author mahirul
 *
 */
public class ppPaddle extends Thread {
	double X;
	double Y;
	double Vx;
	double Vy;
	GRect myPaddle;
	GraphicsProgram GProgram;
	ppTable myTable;
    Color myColor;
/**
 * 
 * @param X - X position of paddle
 * @param Y - Y position of paddle
 * @param myTable - reference to table class
 * @param GProgram - reference to GProgram class
 * implicit constructor
 */
	public ppPaddle (double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram) {
		this.X = X;
		this.Y = Y;
		this.myTable = myTable;
		this.GProgram = GProgram;
		this.myColor = myColor;
		
		// World Coordinates
		double upperLeftX = X - ppPaddleW/2;
		double upperLeftY = Y + ppPaddleH/2;
		
		// P is in screen coordinates
		GPoint p = myTable.W2S(new GPoint(upperLeftX, upperLeftY));
		
		// Screen Coordinates
		double ScrX = p.getX();
		double ScrY = p.getY();
		
		this.myPaddle = new GRect (ScrX , ScrY, ppPaddleW*Xs, ppPaddleH*Ys);
		
		myPaddle.setFilled(true);
		myPaddle.setColor(myColor);
		GProgram.add(myPaddle);
		
		
	}
	/**
	 * Extends the thread to update paddle velocity 
	 * in response to user's changing of position
	 */
	public void run() {
		double lastX = X;
		double lastY = Y;
		while (true) {
		Vx=(X-lastX)/TICK;
		Vy=(Y-lastY)/TICK;
		lastX=X;
		lastY=Y;
		GProgram.pause(TICK*TSCALE); // Time to mS
		}
		}
	/**
	 * Returns paddle location
	 * @return
	 */
	public GPoint getP() {
		return new GPoint (X,Y);
	
	}
	/**
	 * Sets and moves paddle to (X,Y)
	 * @param P - point P
	 */
	public void setP(GPoint P) {
		// update instance variables
		this.X = P.getX();
		this.Y = P.getY();
		
		// World Coordinates
		double upperLeftX = X - ppPaddleW/2;
		double upperLeftY = Y + ppPaddleH/2;
				
		// P is in screen coordinates
		GPoint p = myTable.W2S(new GPoint(upperLeftX, upperLeftY));
				
		// Screen Coordinates
		double ScrX = p.getX();
		double ScrY = p.getY();
		
		// move GRect instance
		this.myPaddle.setLocation(ScrX,ScrY);
	    }
	
	
		/**
		 * return the velocity in x and y direction of the paddle
		 * @return
		 */
		public GPoint getV() {
			return new GPoint(Vx,Vy);
	    }
		
		/**
		 * returns sign of the Y velocity of paddle
		 * @return
		 * return -1 if Vy < 0
		 * return 1 if Vy > 0
		 */
			
		public double getSgnVy() {
			if (Vy >= 0 ) return 1;
			else return -1;
	    }
		
		/**
		 * called when X+Xo >= myPaddle.getP().getX()
		 * true when the position Y of ball is in the Y range of paddle
		 * @param Sx - Xball
		 * @param Sy- Yball
		 * @return
		 */
		public boolean contact(double Sx, double Sy) {
			return ( (Sy >= Y - ppPaddleH/2) && (Sy <= Y + ppPaddleH/2));
			
		}
		
		
}
