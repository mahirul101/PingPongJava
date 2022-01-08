package ppPackage;

import static ppPackage.ppSimParams.*;
import static ppPackage.ppSim.*;



import java.awt.Color;

import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import acm.graphics.GOval;

/**
 * This class contains the simulation loop and extends the Thread class.
 * This allows the methods to be executed simultaneously with other methods.
 * Part of the code are taken from the Assignment 3 solutions document made by Frank P. Ferrie.
 * @author mahirul
 *
 */
	
	public class ppBall extends Thread {
		
		// Instance variables
		
		private double Xinit;                        // Initial position of ball - X
		private double Yinit;                        // Initial position of ball - Y
		private double Vo;                           // Initial velocity (Magnitude)
		private double theta;                        // Initial direction
		private double loss;                         // Energy loss on collision
		private Color color;                         // Color of ball
		private GraphicsProgram GProgram;            // Instance of ppSim class (this)
		GOval myBall;                                // Graphics object representing ball
		ppTable myTable;
		ppPaddle RPaddle;
		ppPaddle LPaddle;
		double X, Xo, Y, Yo;
		double Vx, Vy;
		boolean running;
		
		/**
		* The constructor for the ppBall class copies parameters to instance variables, creates an
		* instance of a GOval to represent the ping-pong ball, and adds it to the display.
		*
		* @param Xinit - starting position of the ball X (meters)
		* @param Yinit - starting position of the ball Y (meters)
		* @param Vo - initial velocity (meters/second)
		* @param theta - initial angle to the horizontal (degrees)
		* @param loss - loss on collision ([0,1])
		* @param color - ball color (Color)
		* @param GProgram - a reference to the ppSim class used to manage the display
		 * @param myTable 
		*/
		
		public ppBall(double Xinit, double Yinit, double Vo, double theta, double loss, Color color , 
				ppTable myTable, GraphicsProgram GProgram) {
			
		this.Xinit=Xinit;                        // Copy constructor parameters to instance variables
		this.Yinit=Yinit;
		this.Vo=Vo;
		this.theta=theta;
		this.loss=loss;
		this.color= color;
		this.GProgram= GProgram;
		this.myTable = myTable;
		
		}
		/**
		* In a thread, the run method is NOT started automatically (like in Assignment 1).
		* Instead, a start message must be sent to each instance of the ppBall class, e.g.,
		* ppBall myBall = new ppBall (--parameters--);
		* myBall.start();
		* The body of the run method is essentially the simulator code you wrote for A1.
		*/
		public void run() {
			
			 GPoint p = myTable.W2S(new GPoint(Xinit,Yinit));		
		        double ScrX = p.getX();						// Convert simulation to screen coordinates
		    	double ScrY = p.getY();			
		    	GOval myBall = new GOval(ScrX,ScrY,2*bSize*Xs,2*bSize*Ys);
		    	myBall.setColor(color);
		    	myBall.setFilled(true);
		    	GProgram.add(myBall);
		    	GProgram.pause(1000);
		    	
		/**
		 * This simulation updates the velocity and position of the ball at each collision 
		 * by resetting the time at 0.
		 */
			
		// Initialize simulation parameters
		    	
		    	double time = 0;                              // time (reset at each interval)
		    	double Vt = bMass*g / (4*Pi*bSize*bSize*k);   // Terminal velocity
		    	double KEx=ETHR,KEy=ETHR;                     // Kinetic energy in X and Y directions
		    	double PE=ETHR;                               // Potential energy                            
		    	//double Xo, X, Vx;                             // X position and velocity variables
		    	//double Yo, Y, Vy;                             // Y position and velocity variables
		    	double Vox=Vo*Math.cos(theta*Pi/180);         // Initial velocity components in X and Y
		    	double Voy=Vo*Math.sin(theta*Pi/180);
		    	Xo=Xinit;                                     // Initial X offset (ball touching wall)
		    	Yo=Yinit;                                     // Initial Y offset                              
		    	
		// Main simulation loop
			
			running = true;						                              // Initial state = falling.
	
			
			while (running) {
				X = Vox*Vt/g*(1-Math.exp(-g*time/Vt));                                // Update relative position
				Y = Vt/g*(Voy+Vt)*(1-Math.exp(-g*time/Vt))-Vt*time;
				Vx = Vox*Math.exp(-g*time/Vt);                                        // and velocity.
				Vy = (Voy+Vt)*Math.exp(-g*time/Vt)-Vt;                                // Equation of PE without energy loss
	    		
            // Check for hitting the ceiling 
				
			    if (Yo+Y > Ymax) {
			    	if (Vx > 0){
			    		PlayerPoints += 1;                     // adds a point for player if ball comes from the left (Vx > 0)
			    		running = false;                       // stops games
			    		 
			    	}
			    	if (Vx < 0) {
			    		AgentPoints += 1;                      // adds a point for agent if ball comes from the right (Vx < 0)
			    		running = false;                       // stops games
			    	}
			    	// records score
			    	APointL.setText("Agent: " + AgentPoints);            
		    		PPointL.setText("Player: " + PlayerPoints);
			    }
				    
		          
	    	// Collision with the ground
				
	    		if (Vy < 0 && Yo+Y <= bSize) {
	    			
	    	
	    	    // Kinetic and Potential energy after collision with ground
	    		
	            KEx=0.5*bMass*Vx*Vx*(1-loss); 
	    		KEy=0.5*bMass*Vy*Vy*(1-loss);
	            PE=0;
	            
	            // Velocity change due to collision with ground
	            
	            Vox=Math.sqrt(2*KEx/bMass); 
	            Voy=Math.sqrt(2*KEy/bMass);
	            if (Vx<0) Vox=-Vox;                          // If ball comes from the right and hits the ground, 
	                                                         // it will continue bouncing to the left (-Vox).
	            
	            //re-initialize time and motion parameters
				
	    	    time=0;                                      // time is reset at every collision
	    	    Xo+=X;                                       // need to accumulate distance between collisions 
	    		Yo=bSize;                                    // the absolute position of the ball on the ground 
	    		X=0;                                         // (X,Y) is the instantaneous position along an arc, 
	    		Y=0;                                         // Absolute position is (Xo+X,Yo+Y).
	    			
	            
	    		// Simulation ends when energy is at minimum and only when it's on the ground since PE ~ 0.
	    		
	            if ((KEx+KEy+PE)<ETHR) running=false;
	            
	    		}
	    		
	    	//Collision with the right wall (Vx > 0 and X >= xMax)
	    		
	    		if (Vx > 0 && (Xo+X)>= (RPaddle.getP().getX() - ppPaddleW/2 - bSize)) {

	    		// Possible collisions
	    			
	    		if (RPaddle.contact(X+Xo,Y+Yo)) {
	            // Kinetic and Potential energy change after collision with right wall
	    			
	            KEx=0.5*bMass*Vx*Vx*(1-loss); 
	        	KEy=0.5*bMass*Vy*Vy*(1-loss);
	            PE=bMass*g*(Y+Yo);
	                
	            // Velocity change after collision with right wall
	                
	            Vox=-Math.sqrt(2*KEx/bMass);                      
                Voy=Math.sqrt(2*KEy/bMass);
                 
                //Velocity change with gain factors
                
                Vox=Vox*ppPaddleXgain;                    // Scale X component of velocity
                Voy=Voy*ppPaddleYgain*RPaddle.getSgnVy(); // Scale Y + same dir. as paddle 
               
                if (Vox > VoxMAX) Vox=-Math.sqrt(2*KEx/bMass);
	            //re-initialize time and motion parameters
				
	    		time=0;
	            Xo=RPaddle.getP().getX() - ppPaddleW/2 - bSize;                                   // X position right after collision is on the right wall
	            Yo+=Y;
	            X=0;
	            Y=0;
	            
	    		} else {  
	    			AgentPoints += 1;                                    // adds point for agent if player misses
	    			running = false;
	    			// records score
	    			APointL.setText("Agent: " + AgentPoints);
		    		PPointL.setText("Player: " + PlayerPoints);
	    		 }
	    		}
	    		
	    	//Collision with the left wall (Vx < 0 and X <= Xinit)
	    		
	            if (Vx < 0 && (Xo+X)<= (LPaddle.getP().getX() + ppPaddleW/2 + bSize)) {
	            
	            // Possible collisions
	    			
		    	if (LPaddle.contact(X+Xo,Y+Yo)) {
	           
	            // Kinetic and Potential energy change after collision with left wall
	    			
	    		KEx=0.5*bMass*Vx*Vx*(1-loss); 
	            KEy=0.5*bMass*Vy*Vy*(1-loss);
	            PE=bMass*g*(Y+Yo);
	                
	            // Velocity change
	               		
	            Vox=Math.sqrt(2*KEx/bMass); 
	            Voy=Math.sqrt(2*KEy/bMass);
	                
	            if (Vy < 0) Voy = -Voy;
	            
                //Velocity change with gain factors
                
                Vox=Vox*ppPaddleXgain;                    // Scale X component of velocity
                Voy=Voy*ppPaddleYgain*LPaddle.getSgnVy(); // Scale Y + same dir. as paddle 
               
                if (Vox > VoxMAX) Vox=Math.sqrt(2*KEx/bMass);
	            //re-initialize time and motion parameters
				
	    		time=0;
	            Xo=LPaddle.getP().getX() + ppPaddleW/2 + bSize;                                       // X position right after collision is on the left wall.
	            Yo+=Y;
	            X=0;
	            Y=0;
		    	}
		    	else {
		    		PlayerPoints += 1;                                   // adds points for player if agent misses
		    		running = false;
		    		// records score
		    		APointL.setText("Agent: " + AgentPoints);
		    		PPointL.setText("Player: " + PlayerPoints);
		    		
		    	}
	         
	    		}
	            
	      
	            
	            // Update and display
	            
	            p = myTable.W2S(new GPoint(Xo+X-bSize,Yo+Y+bSize));            // Get current position in screen coordinates
	    	    ScrX = p.getX();
	    	    ScrY = p.getY();
	    	    
	    	    if (traceButton.isSelected()) 
	    	    	trace(Xo+X,Yo+Y);                                
	    	    myBall.setLocation(ScrX,ScrY);                         // Change position of ball in display
	    	                                  
	    	    
	    	    time+=TICK;
	    	    GProgram.pause(t.getValue());                          // gets value of tick using JSlider of tick (t)
	    	    if((KEx+KEy+PE)<ETHR) running=false;
	    	    
	            }
		     }
		
	    
	    /**
	     * Plot points on the screen to form traces behind the ball
	     * @param X - location of the point on x-axis
	     * @param Y - location of the point on y-axis
	     */
	    
	    public void trace(double X, double Y) {
	    	GPoint p = myTable.W2S(new GPoint(X,Y));
	    	double ScrX = p.getX();
	    	double ScrY = p.getY();
	    	GOval pt = new GOval(ScrX,ScrY,PD,PD);
	    	pt.setColor(Color.BLACK);
	    	pt.setFilled(true);
	    	GProgram.add(pt);
	    	
		
			}
	    
	    /**
	     * Setter for right paddle
	     * @param myPaddle
	     */
	    
	    public void setRightPaddle(ppPaddle myPaddle) {
	    	this.RPaddle = myPaddle;
	    }
	    
	    /**
	     * Sets value of reference to Agent Paddle
	     * @param LPaddle
	     */
	    
	    public void setLeftPaddle(ppPaddle LPaddle) {
	    	this.LPaddle = LPaddle;
	    }
	    
	    /**
	     * Ball velocity getter
	     * @return
	     */
	    
	    public GPoint getV() {
	    	return new GPoint(Vx,Vy);
	    }
	    
	    /**
	     * Ball position getter
	     * @return
	     */
	    
	    public GPoint getP() {
	    	return new GPoint(X+Xo,Y+Yo);
	    }
	    
	    /**
	     * Ends simulation
	     */
	   
	    public void kill() {
	    	running=false;
	    }
		
	}




















