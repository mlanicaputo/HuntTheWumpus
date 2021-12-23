/*
  Template created by Bruce A. Maxwell and Stephanie R Taylor

/*
Milo Lani-Caputo
12/21/21
HuntTheWumpus.java
Hunt the Wumpus
*/

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Point;

import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

import java.util.*;

/*
This class creates a game and uses several built-in Java classes to display the game as it is played.
*/

/**
 * Creates a window with two text fields, one buttons, and a large
 * display area. The app then tracks the mouse over the display area.
 **/
public class HuntTheWumpus {

    // These four fields are as in the LandscapeDisplay class (though 
    // they are now all private)
    private JFrame win;
    private LandscapePanel canvas;    
    private Landscape scape; 
    private int scale;

    // Milo's added fields:
    private Graph graphField;
    private Hunter hunt;
    private Wumpus wump;

    /** fields related to demo of mouse interaction **/
    // Unless you have a good reason to report the mouse position in
    // HuntTheWumpus, you should remove these fields and all the
    // code that affects them.
    // There here to demonstrate how you would add a message to the bottom
    // of the window. For HuntTheWumpus, you may want to use it to indicate
    // that the Hunter is armed or close to the Wumpus, or dead.
    JLabel fieldX; // Label field 1, displays the X location of the mouse 
    JLabel fieldY; // Label field 2, displays the Y location of the mouse 

    // controls whether the game is playing or exiting
    private enum PlayState { PLAY, STOP }
    private PlayState state;

    /**
     * Creates the main window
     * @param scape the Landscape that will hold the objects we display
     * @param scale the size of each grid element
     **/		 
    public HuntTheWumpus() {
        // The game should begin in the play state.
        this.state = PlayState.PLAY; 
        
        // Create the elements of the Landscape and the game.
        this.scale = 64; // determines the size of the grid
        this.scape = new Landscape(scale*10,scale*10);
        // This is test code that adds a few vertices.
        // You will need to update this to make a Graph first, then
        // add the vertices to the Landscape.

        // initialize the graph field with a graph of 64 vertices
        this.graphField = new Graph(64);


        // iterate through the graph's vertices
        for (int vert = 0; vert < this.graphField.vertexCount(); vert++){

            // create a temporary reference to the current vertex
            Vertex current = this.graphField.get(vert);

            // modify the graph's vector's position fields
            current.setYPos(vert / 8);
            current.setXPos(vert % 8);

            // connect the vertex to the vertices above/below/to the sides of it

            // create ints to correspond to the directions
            int northY = current.getYPos() - 1;
            if (northY < 0){
                northY += 8;
            }

            int southY = current.getYPos() + 1;
            if (southY > 7){
                southY -= 8;
            }

            int eastX = current.getXPos() + 1;
            if (eastX > 7){
                eastX -= 8;
            }

            int westX = current.getYPos() - 1;
            if (westX < 0){
                westX += 8;
            }

            current.connect(this.graphField.get(current.getXPos() + 8 * northY), Vertex.Direction.NORTH);
            current.connect(this.graphField.get(current.getXPos() + 8 *southY), Vertex.Direction.SOUTH);
            current.connect(this.graphField.get(eastX + 8 * current.getYPos()), Vertex.Direction.EAST);
            current.connect(this.graphField.get(westX + 8 * current.getYPos()), Vertex.Direction.WEST);
        }


        // create a random object
        Random rand = new Random();

        // set the location of the wumpus
        this.scape.wump.setLoc(this.graphField.get(rand.nextInt(64)));
        // set the location of the hunter to a position that is not the same as the location of the wumpus
        this.scape.hunt.setLoc(this.graphField.get(rand.nextInt(64)));
        while (this.scape.wump.getLocation() == this.scape.hunt.getLocation()){
            this.scape.hunt.setLoc(this.graphField.get(rand.nextInt(64)));
        }


        // add the graph's vertices to the landscape's list field
        // iterate through the graph's vertices
        for (int vert = 0; vert < this.graphField.vertexCount(); vert++){
            // add each vertex to the Landscape's linked list
            this.scape.list.addFirst(this.graphField.get(vert));
            // set the distance of the current vertex to the distance from the wumpus vertex
            this.graphField.get(vert).setDistanceFromRoot(this.graphField.get(vert).distance(this.scape.wump.getLocation()));
        }
        
        
        // Make the main window
        this.win = new JFrame("Basic Interactive Display");
        win.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);

        // make the main drawing canvas (this is the usual
        // LandscapePanel we have been using). and put it in the window
        this.canvas = new LandscapePanel( this.scape.getWidth(),
					                                        this.scape.getHeight() );
        this.win.add( this.canvas, BorderLayout.CENTER );
        this.win.pack();

        // make the labels and a button and put them into the frame
        // below the canvas.
        this.fieldX = new JLabel("X");
        this.fieldY = new JLabel("Y");
        JButton quit = new JButton("Quit");
        JPanel panel = new JPanel( new FlowLayout(FlowLayout.RIGHT));
        panel.add( this.fieldX );
        panel.add( this.fieldY );
        panel.add( quit );
        this.win.add( panel, BorderLayout.SOUTH);
        this.win.pack();

        // Add key and button controls.
        // We are binding the key control object to the entire window.
        // That means that if a key is pressed while the window is
        // in focus, then control.keyTyped will be executed.
        // Because we are binding quit (the button) to control, control.actionPerformed will
        // be called if the quit button is pressed. If you make more than one button,
        // then the same function will be called. Use an if-statement in the function
        // to determine which button was pressed (check out Control.actionPerformed and
        // this advice should make sense)
        Control control = new Control();
        this.win.addKeyListener(control);
        this.win.setFocusable(true);
        this.win.requestFocus();
        quit.addActionListener( control );

        // for mouse control
        // Make a MouseControl object and then bind it to the canvas
        // (the part that displays the Landscape). When the mouse
        // enters, exits, moves, or clicks in the canvas, the appropriate
        // method will be called.
        MouseControl mc = new MouseControl();
        this.canvas.addMouseListener( mc );
        this.canvas.addMouseMotionListener( mc );

        // The last thing to do is make it all visible.
        this.win.setVisible( true );
    }


    private class LandscapePanel extends JPanel {
        /**
         * Creates the drawing canvas
         * @param height the height of the panel in pixels
         * @param width the width of the panel in pixels
         **/
        public LandscapePanel(int height, int width) {
            super();
            this.setPreferredSize( new Dimension( width, height ) );
            this.setBackground(Color.white);
        }

        /**
         * Method overridden from JComponent that is responsible for
         * drawing components on the screen.  The supplied Graphics
         * object is used to draw.
         * 
         * @param g		the Graphics object used for drawing
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            scape.draw( g, scale );
        }
    } // end class LandscapePanel


    // This is the class where you define functions that are 
    // executed when certain mouse events take place.
    private class MouseControl extends MouseInputAdapter {
        public void mouseMoved(MouseEvent e) {
            fieldX.setText( "" + e.getPoint().x );
            fieldY.setText( "" + e.getPoint().y );
        }

        public void mouseDragged(MouseEvent e) {
            fieldX.setText( "" + e.getPoint().x );
            fieldY.setText( "" + e.getPoint().y );
        }
        
        public void mousePressed(MouseEvent e) {
            System.out.println( "Pressed: " + e.getClickCount() );
        }

        public void mouseReleased(MouseEvent e) {
            System.out.println( "Released: " + e.getClickCount());
        }

        public void mouseEntered(MouseEvent e) {
            System.out.println( "Entered: " + e.getPoint() );
        }

        public void mouseExited(MouseEvent e) {
            System.out.println( "Exited: " + e.getPoint() );
        }

        public void mouseClicked(MouseEvent e) {
    	    System.out.println( "Clicked: " + e.getClickCount() );
        }
    } // end class MouseControl


    private class Control extends KeyAdapter implements ActionListener {

        boolean firingMode = false;

        public void keyTyped(KeyEvent e) {
            System.out.println( "Key Pressed: " + e.getKeyChar() );
            if( ("" + e.getKeyChar()).equalsIgnoreCase("q") ) {
                state = PlayState.STOP;
            }

            // four things you can do: w, a, s, d, space

            // if w is pressed
            if( ("" + e.getKeyChar()).equalsIgnoreCase("w") ) {

                // if firingMode is true
                if (firingMode == true){
                    // if the wumpus is north of the hunter
                    if (scape.hunt.getLocation().getAdjacent(Vertex.Direction.NORTH) == scape.wump.getLocation()){
                        // set the wumpus to dead
                        scape.wump.setAlive(false);
                        // end the game
                        state = PlayState.STOP;
                        return;
                    }
                    // else if the wumpus is not north of the hunter
                    else {
                        // set the hunter to dead
                        scape.hunt.setAlive(false);
                        // end the gaem
                        state = PlayState.STOP;
                        return;
                    }
                }

                // move the hunter north
                scape.hunt.move(Vertex.Direction.NORTH);
                // check to see if the hunter's vertex is the same as the wumpus's vertex
                if (scape.hunt.getLocation() == scape.wump.getLocation()){
                    // set the hunter to dead
                    scape.hunt.setAlive(false);
                    // end the game
                    state = PlayState.STOP;
                }
            }

            // if d is pressed
            if( ("" + e.getKeyChar()).equalsIgnoreCase("d") ) {

                // if firingMode is true
                if (firingMode == true){
                    // if the wumpus is north of the hunter
                    if (scape.hunt.getLocation().getAdjacent(Vertex.Direction.EAST) == scape.wump.getLocation()){
                        // set the wumpus to dead
                        scape.wump.setAlive(false);
                        // end the game
                        state = PlayState.STOP;
                        return;
                    }
                    // else if the wumpus is not north of the hunter
                    else {
                        // set the hunter to dead
                        scape.hunt.setAlive(false);
                        // end the gaem
                        state = PlayState.STOP;
                        return;
                    }
                }

                // move the hunter east
                scape.hunt.move(Vertex.Direction.EAST);
                // check to see if the hunter's vertex is the same as the wumpus's vertex
                if (scape.hunt.getLocation() == scape.wump.getLocation()){
                    // set the hunter to dead
                    scape.hunt.setAlive(false);
                    // end the game
                    state = PlayState.STOP;
                }
            }

            // if s is pressed
            if( ("" + e.getKeyChar()).equalsIgnoreCase("s") ) {

                // if firingMode is true
                if (firingMode == true){
                    // if the wumpus is north of the hunter
                    if (scape.hunt.getLocation().getAdjacent(Vertex.Direction.SOUTH) == scape.wump.getLocation()){
                        // set the wumpus to dead
                        scape.wump.setAlive(false);
                        // end the game
                        state = PlayState.STOP;
                        return;
                    }
                    // else if the wumpus is not north of the hunter
                    else {
                        // set the hunter to dead
                        scape.hunt.setAlive(false);
                        // end the gaem
                        state = PlayState.STOP;
                        return;
                    }
                }

                // move the hunter south
                scape.hunt.move(Vertex.Direction.SOUTH);
                // check to see if the hunter's vertex is the same as the wumpus's vertex
                if (scape.hunt.getLocation() == scape.wump.getLocation()){
                    // set the hunter to dead
                    scape.hunt.setAlive(false);
                    // end the game
                    state = PlayState.STOP;
                }
            }

            // if a is pressed
            if( ("" + e.getKeyChar()).equalsIgnoreCase("a") ) {

                // if firingMode is true
                if (firingMode == true){
                    // if the wumpus is north of the hunter
                    if (scape.hunt.getLocation().getAdjacent(Vertex.Direction.WEST) == scape.wump.getLocation()){
                        // set the wumpus to dead
                        scape.wump.setAlive(false);
                        // end the game
                        state = PlayState.STOP;
                        return;
                    }
                    // else if the wumpus is not north of the hunter
                    else {
                        // set the hunter to dead
                        scape.hunt.setAlive(false);
                        // end the gaem
                        state = PlayState.STOP;
                        return;
                    }
                }

                // move the hunter east
                scape.hunt.move(Vertex.Direction.WEST);
                // check to see if the hunter's vertex is the same as the wumpus's vertex
                if (scape.hunt.getLocation() == scape.wump.getLocation()){
                    // set the hunter to dead
                    scape.hunt.setAlive(false);
                    // end the game
                    state = PlayState.STOP;
                }
            }

            // if space is pressed
            if( ("" + e.getKeyChar()).equalsIgnoreCase(" ") ) {
                // set firingMode to true
                firingMode = true;
                System.out.println("firing mode engaged");
            }
        }

        public void actionPerformed(ActionEvent event) {
            // If the Quit button was pressed
            if( event.getActionCommand().equalsIgnoreCase("Quit") ) {
		        System.out.println("Quit button clicked");
                state = PlayState.STOP;
            }
        }
    } // end class Control

    public void repaint() {
    	this.win.repaint();
    }

    public void dispose() {
	    this.win.dispose();
    }


    public static void main(String[] argv) throws InterruptedException {
        HuntTheWumpus w = new HuntTheWumpus();
        while (w.state == PlayState.PLAY) {
            w.repaint();
            Thread.sleep(33);
        }
        

        // if the hunter is dead, print you lose
        if (w.scape.hunt.getAlive() == false){
            System.out.println("YOU LOSE");
        }
        // else if the hunter is alive, print you win
        else {
            System.out.println("YOU WIN");
        }

        System.out.println(
            "Hunter location: (" + w.scape.hunt.getXPos() + ", " + + w.scape.hunt.getYPos() + ")\n" + 
            "Wumpus location: (" + w.scape.wump.getXPos() + ", " + + w.scape.wump.getYPos() + ")\n"
        );

        System.out.println("Disposing window");
        w.dispose();
    }
}