package ppPackage;
import static ppPackage.ppSimParams.*;
import static ppPackage.ppSim.*;

import java.awt.Color;


import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

/**
 * This class extends ppPaddle by providing information about the 
 * ball's position in order to match the paddle's Y position with the ball's in
 * the run method.
 * Part of this code was taken from Assignment 4 document by Frank P. Ferrie and
 * from Katrina Poulin's tutorial.
 * @author mahirul
 *
 */

public class ppPaddleAgent extends ppPaddle {
	
	ppBall myBall;
	/**
	 * Constructor of ppPaddleAgents, same as superclass
	 * @param X - x position
	 * @param Y - y position
	 * @param myColor - color of paddle
	 * @param myTable - implements ppTable class
	 * @param GProgram - implements GraphicsProgram class
	 */
	public ppPaddleAgent(double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram) {
		super(X, Y, myColor, myTable, GProgram);
	}
	
	/**
	 * Method to track the ball's position and match with the paddle's position
	 * so that the paddle always hits the ball
	 */
	public void run() {
		
		int ballSkip = 0;
		int AgentLag = lag.getValue();
		double lastX = X;
		double lastY = Y;
				
		while(true) {
		
			GProgram.pause(TICK*TSCALE);
			Vx =  (X-lastX)/TICK;
			Vy = (Y-lastY)/TICK;
			lastX = X;
			lastY = Y;
		
			if (ballSkip++ >= AgentLag) {
				
				//get the ball y position
				double Y = myBall.getP().getY();
				
				//set paddle position to that y
				this.setP(new GPoint(this.getP().getX(), Y));
				ballSkip = 0;
				
			}
				
			}
			
		}
	
	/**
	 * Sets value of myBall in ppPAddleAgent
	 * @param myBall
	 */
	public void attachBall(ppBall myBall) {
		this.myBall = myBall;
	}

}


