# PingPongJava
This is a Ping Pong game programmed using Java and Eclipse IDE in which a player and a computer agent simulate the game of ping pong. 

## Description:

In fact, the paddle on the right is controlled by the user's cursor while the one on the right is a computer. The trajectory of the ball could be shown using the toggle button "trace". The difficulty of the game can also be controlled using the sliders "lag" and "tick" which controls the computer's response time and the travel time of the ball, respectively. 

The following is a GIF represeting a short gameplay of the program:

![](PingPong.gif)

### Classes:

Here are the 6 classes that were made in order to run this program:
- ppSimParams: This class contains all the constant definitions needed to run the program.
- ppTable: This class consists of a constructor that sets up the ground plane and converts world coordinates to screen coordiantes and vice-versa.
- ppPaddle: This class creates a paddle instance and exports methods to interact with the paddle.
- ppPaddleAgent:  This class extends ppPaddle by providing information about the  ball's position in order to match the paddle's Y position with the ball's in the run method. It basically controls the computer's paddle.
- ppBall: This class contains the simulation loop and extends the Thread class. This allows the methods to be executed simultaneously with other methods. The ball's energy, position and velocity after each collision is set in this class.
- ppSim: This class has a main method and executes the ping-pong game.

ACM Graphics Program is used in this program, therefore, it should be downloaded and added in the "Add External Jar" that can be accessed through "Build Path".

### Buttons:

- Clear: Restart the game and the score board
- New Serve: Restart the game without resetting the score board
- Quit: Quit the game
- Trace: Toggle button that lets the user choose whether they want the ball's trajectory or not
- Lag: Controls the computer's response time (higher response time means more lag, therefore, it's easier to win)
- Tick: Control the travel time of the ball (higher tick means the ball's movement is laggy, therefore, it's easier to win)

## References:
- This project was made for an Introduction to Software Development class (ECSE 202) at McGill, therefore, part of the code is taken from Prof. Frank Ferrie and the TA Katrina Poulin.






