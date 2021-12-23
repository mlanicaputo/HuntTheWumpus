/*
Milo Lani-Caputo
12/21/21
Hunter.java
Hunt the Wumpus
*/

import java.awt.Graphics;
import java.awt.Color;

/*
Hunter class used to create a Hunter object for the HuntTheWumpus game.
*/

public class Hunter{

    private Vertex location;
    private boolean isAlive;
    private boolean arrowFired;

    public Hunter(){
        // constructor method
        this.location = null;
        this.isAlive = true;
        this.arrowFired = false;
    }

    public int getXPos(){
        // returns the x component of the location vertex
        return this.location.getXPos();
    }

    public int getYPos(){
        // returns the y component of the location vertex
        return this.location.getYPos();
    }

    public Vertex getLocation(){
        // returns the hunter's location vertex
        return this.location;
    }

    public void setLoc(int x, int y){
        // sets the hunter's location to a new vertex coordinate
        this.location = this.location.getNeighbor(x, y);
        // sets location to visible
        this.location.setVisible(true);
    }

    public void setLoc(Vertex v){
        // sets the hunter's location to a new vertex coordinate
        this.location = v;
    }

    public boolean getAlive(){
        // returns true if the hunter is alive, false if dead
        return this.isAlive;
    }

    public void setAlive(boolean status){
        // sets the alive status of the Hunter
        this.isAlive = status;
    }

    public void fireArrow(){
        // fires the hunter's arrow
        this.arrowFired = true;
    }

    public void move(Vertex.Direction dirn){
        // moves the hunter in the direction
        this.location = this.location.getAdjacent(dirn);
        // set this location to visible
        this.location.setVisible(true);
    }

    public void draw(Graphics g, int scale){
        // draw method for the Hunter

        int xpos = (int)this.getXPos()*scale;
        int ypos = (int)this.getYPos()*scale;
        int border = 2;
        
        // set the color
        g.setColor(Color.blue);
        
        g.drawOval(xpos + border, ypos + border, scale - 2*border, scale - 2 * border);
    }
}