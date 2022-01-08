package ppPackage;

import static ppPackage.ppSimParams.*;

import java.awt.Color;

import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

/**
 * This class consists of a constructor that sets up the ground plane
 * Part of this code is taken from the Assignment 3 solutions document made by Frank P. Ferrie
 * and from Katrina Poulin's tutorial.
 * @author mahirul
 *
 */
public class ppTable {
	
	GraphicsProgram GProgram;
	
	/**
	 * This constructor is to create a plane and walls on the screen
	 * @param GProgram - creates graphical objects of many kinds
	 */
	
	public ppTable(GraphicsProgram GProgram) {
	this.GProgram = GProgram;
	
	// Create the ground plane using the method to draw ground plane
	
	drawGroundPlane();
    }
	
	/**
	 * Convert from world to screen coordinates 
	 * since mouse returns location in screen coordinates
	 * @param P - point in world coordinates
	 * @return - the same P but in screen coordinates
	 */

    public GPoint W2S (GPoint P) {
    	return new GPoint((P.getX()-Xmin)*Xs,ymax-(P.getY()-Ymin)*Ys); 
    	}
    
    /**
     * Convert from screen to world coordinates
     * @param P - point in screen coordinates
     * @return - point in world coordinates
     */
    
    public GPoint S2W (GPoint P) {
    	double ScrX = P.getX();
    	double ScrY = P.getY();
   
    	return new GPoint (ScrX/Xs+Xmin,(ymax- ScrY)/Ys + Ymin);
    }
    
    /**
     * method to draw ground plane
     */
    
    public void drawGroundPlane() {
    	GRect gPlane = new GRect(0,HEIGHT,WIDTH+OFFSET,3);	               // A thick line HEIGHT pixels down from the top
    	gPlane.setColor(Color.BLACK);                                      // Color the plane black
    	gPlane.setFilled(true);                                            // Fill the plane 
    	GProgram.add(gPlane);
    	
    }

 
    /**
     * All objects on the display (except buttons) will be erased so the user can start a new game
     */
    
    public void newScreen() {
    	GProgram.removeAll();
    	drawGroundPlane();
    }
	
}
