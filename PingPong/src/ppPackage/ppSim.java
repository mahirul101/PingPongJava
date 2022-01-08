package ppPackage;

import static ppPackage.ppSimParams.*;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

/**
 * This class has a main method and executes the ping-pong game.
 * Part of this code is taken from the Assignment 3 solutions made by Frank P. Ferrie
 * and from Katrina Poulin's tutorial.
 * @author mahirul
 *
 */
public class ppSim extends GraphicsProgram {
	
	ppTable myTable;
	ppPaddle RPaddle;
	ppPaddleAgent LPaddle;
	ppBall myBall;
	RandomGenerator rgen;
	
	
	/**
	*This is a main method that is public so it can be accessed by any class.
	*String[] args are the paramaters of the main method.
	*This method does not return any value.
	*/
	
    public static void main(String[] args) {
         new ppSim().start(args);
    }
    
    /**
     * This method implements a simple user interface to allow the user
     * to start a new game, reset the score board, quit the game 
     * and enable/disable the tracings. It does so by enabling ActionListeners.
     */
    
    // creates object for JSliders (public) so they can be accessed by other classes
 	public static JSlider lag = new JSlider(0, 20, 0);
 	public static JSlider t = new JSlider(0, 50, 20);
 	
 	public static int AgentPoints = 0;
 	public static int PlayerPoints = 0;
 	
 	// creates objects for JLabels so they can be accessed by other classes
 	public static JLabel APointL = new JLabel("Agent: " + AgentPoints);
 	public static JLabel PPointL = new JLabel("Player: " + PlayerPoints);
 
    
    public void init() {
    	this.resize(xmax+OFFSET,ymax+OFFSET);
    	
    	 /**
    	 * Create 4 buttons (Clear, New serve, Quit, Trace)
         * JButton - trigger actions in code
         * JToggle Button - 2 states: true and false
         */
    	
    	JButton clearButton = new JButton("Clear");                            // Clear points
    	JButton newServeButton = new JButton("New Serve");                     // Starts a new game
    	JButton quitButton = new JButton("Quit");                              // Quits the game
        traceButton = new JToggleButton("Trace", false);                       // Traces the ball trajectory is button is pressed
       
    	// add buttons
        add(clearButton, SOUTH);
    	add(newServeButton, SOUTH);
    	add(quitButton, SOUTH);
    	add(traceButton, SOUTH);
    	
    	// add lag slider
    	add(new JLabel("-lag (0)"), SOUTH);      
    	add(lag, SOUTH);
    	add(new JLabel("+lag (20)"), SOUTH);
  
    	
    	// add tick slider
    	add(new JLabel("-tick (0 ms)"), SOUTH);
    	add(t, SOUTH);
    	add(new JLabel("+tick (50 ms)"), SOUTH);
    	
    	// add score board
    	add(APointL, NORTH);
    	add(PPointL, NORTH);
    	
    	addMouseListeners();
    	addActionListeners();
    	
    	rgen = RandomGenerator.getInstance();
    	rgen.setSeed(RSEED);
    	
    	myTable = new ppTable(this);
    	myBall = newBall();
    	newGame();
    }
    /**
     * generates random parameters for the ball 
     * and returns an instance of the myBall
     * @return
     */
    ppBall newBall() {
    	
    	// generate parameters for simulation
    	Color iColor = Color.RED;
        double iYinit = rgen.nextDouble(YinitMIN,YinitMAX);
        double iLoss = rgen.nextDouble(EMIN,EMAX);
        double iVel = rgen.nextDouble(VoMIN,VoMAX);
        double iTheta = rgen.nextDouble(ThetaMIN,ThetaMAX);
        
        // create ball
        return myBall = new ppBall(Xinit, iYinit, iVel, iTheta, iLoss, iColor, myTable, this);
    	
    }
    
    /**
     * This method sets up the display, creates the ball and paddles, attaches left ball to left paddle,
     * calls setRightPaddle and setLeftPaddle. This method is used when the JButton New serve is pressed.
     */
    
    public void newGame() {
    	 if (myBall != null) myBall.kill();          // stop current game in play
    	 myTable.newScreen();
    	 myBall = newBall();
    	 RPaddle = new ppPaddle(ppPaddleXinit, ppPaddleYinit, Color.GREEN, myTable, this);
    	 LPaddle = new ppPaddleAgent(LPaddleXinit, LPaddleYinit, Color.BLUE, myTable, this);
    	 LPaddle.attachBall(myBall);
    	 myBall.setRightPaddle(RPaddle);
    	 myBall.setLeftPaddle(LPaddle);
    	 pause(STARTDELAY);
    	 myBall.start();
       	 LPaddle.start();
       	 RPaddle.start();
    }

    	
    
    /**
    * Mouse Handler - a moved event moves the paddle up and down in Y
    */
    
    public void mouseMoved(MouseEvent e) {
    	if (myTable==null || RPaddle==null) return;
    	
    	// convert mouse position to a point in screen coordinates
    	GPoint Pm = myTable.S2W(new GPoint(e.getX(),e.getY()));
    	double PaddleX = RPaddle.getP().getX();
    	double PaddleY = Pm.getY();
    	RPaddle.setP(new GPoint(PaddleX,PaddleY));
    }
    
    /**
     * Button handler 
     * @param e
     */
    
    public void actionPerformed(ActionEvent e) {
    	String command = e.getActionCommand();
    	if (command.equals("New Serve")) {
    		newGame();
    	}
    	if (command.equals("Clear")) {
    		AgentPoints = 0;
    		PlayerPoints = 0;
    		APointL.setText("Agent: " + AgentPoints);
    		PPointL.setText("Player: " + PlayerPoints);
    		
    	}
    	
    	else if (command.equals("Quit")) {
    		System.exit(0);
    	}
    		
    		
    }
    
    
}



