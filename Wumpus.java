/*
Milo Lani-Caputo
12/21/21
Wumpus.java
Hunt the Wumpus
*/

import java.awt.Graphics;
import java.awt.Color;

/*
Wumpus class used to create a Wumpus object for the HuntTheWumpus game.
*/

public class Wumpus{

    private Vertex location;
    private boolean isAlive;
    private boolean isVisible;

    public Wumpus(){
        // constructor method
        this.location = null;
        this.isAlive = true;
        this.isVisible = false;
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
        // returns the wumpus's location vertex
        return this.location;
    }

    public void setLoc(int x, int y){
        // sets the wumpus's location to a new vertex coordinate
        this.location = this.location.getNeighbor(x, y);
    }

    public void setLoc(Vertex v){
        // sets the wumpus's location to a new vertex coordinate
        this.location = v;
    }

    public boolean getAlive(){
        // returns true if the Wumpus is alive, false if dead
        return this.isAlive;
    }

    public void setAlive(boolean status){
        // sets the alive status of the Wumpus
        this.isAlive = status;
    }

    public void setVisible(boolean status){
        // sets the visible status of the Wumpus
        this.isVisible = status;
    }

    public void draw(Graphics g, int scale){
        // draw method for the Wumpus

        // if the Wumpus is not visible
        if (!this.isVisible){
            return;
        }

        int xpos = (int)this.getXPos()*scale;
        int ypos = (int)this.getYPos()*scale;
        int border = 2;
        
        // set the color
        // if the wumpus is alive
        if (this.getAlive() == true){
            g.setColor(Color.red);
        }
        else {// if the wumpus is dead
            g.setColor(Color.green);
        }
        
        g.drawOval(xpos + border, ypos + border, scale - 2*border, scale - 2 * border);
    }
}