/*
Milo Lani-Caputo
12/21/21
Vertex.java
Hunt the Wumpus
*/

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;

/*
This is the vertex class used to create graphs in the Graph class.
*/

public class Vertex implements Comparable<Vertex>{

    private ArrayList<Vertex> adjacents;
    private int xPos;
    private int yPos;
    private boolean visible;

    private double distance;
    private boolean visited;
    private Vertex parent;


    public enum Direction{
        NORTH, EAST, SOUTH, WEST
    }// order of adjacents^


    public Vertex(int xCor, int yCor){
        // constructor, initialize all the fields
        this.adjacents = new ArrayList<Vertex>();

        this.xPos = xCor;
        this.yPos = yCor;
        this.visible = false;

        this.distance = 0;
        this.visited = false;
        this.parent = null;
    }


    public Vertex(){
        // constructor, initialize all the fields
        this.adjacents = new ArrayList<Vertex>(4);

        for (int i = 0; i < 4; i++){
            this.adjacents.add(null);
        }

        this.xPos = 0;
        this.yPos = 0;
        this.visible = false;

        this.distance = 0;
        this.visited = false;
        this.parent = null;
    }


    public ArrayList<Vertex> getAdjacentList(){
        // accessor method for the vertex's adjacent list
        return this.adjacents;
    }


    public Vertex getAdjacent(Direction dirn){
        // returns the vertex at the index in the adjacent list

        int index = 0;

        if (dirn == Direction.NORTH){
            index = 0;
        }
        else if (dirn == Direction.EAST){
            index = 1;
        }
        else if (dirn == Direction.SOUTH){
            index = 2;
        }
        else {
            index = 3;
        }

        return this.adjacents.get(index);
    }


    public Vertex getAdjacent(int index){
        // returns the vertex at the index in the adjacent list

        return this.adjacents.get(index);
    }


    public Vertex getNeighbor(int x, int y){
        // returns the Vertex at position (x, y) if the Vertex is in the adjacency list,
        // otherwise null.

        // loop through the adjacent vertices
        for (Vertex v : this.adjacents){
            // check to see if v is null
            if (v != null){
                // check each to see if it has the desired coordinates
                if (v.getXPos() == x && v.getYPos() == y){
                    // if so, return that vertex
                    return v;
                }
            }
        }

        return null;
    }


    public int numNeighbors(){
        // returns the number of connected vertices

        return this.adjacents.size();
    }


    public int getXPos(){
        // return the x position of the vertex
        return this.xPos;
    }


    public int getYPos(){
        // return the y position of the vertex
        return this.yPos;
    }


    public void setXPos(int newX){
        // sets the x position of the vertex
        this.xPos = newX;
    }

    public void setYPos(int newY){
        // sets the y position of the vertex
        this.yPos = newY;
    }


    public void setPos(int newX, int newY){
        // sets the x and y position of the vertex
        this.xPos = newX;
        this.yPos = newY;
    }


    public boolean getVisible(){
        // returns the visible state of the vertex
        return this.visible;
    }


    public void setVisible(boolean newVis){
        // sets the visible status of the vertex
        this.visible = newVis;
    }


    public double getDistanceFromRoot(){
        // returns the distance of this vertex from the root
        return this.distance;
    }


    public void setDistanceFromRoot(double dist){
        // sets the distance from the root
        this.distance = dist;
    }


    public boolean getVisited(){
        // returns the visited status of the vertex
        return this.visited;
    }


    public void setVisited(boolean newVis){
        // sets the visited status of the vertex
        this.visited = newVis;
    }


    public Vertex getParent(){
        // returns the parent vertex of this vertex
        return this.parent;
    }


    public void setParent(Vertex newParent){
        // sets the parent vertex of this vertex
        this.parent = newParent;
    }


    public double distance(Vertex other){
        // returns the Euclidean distance between this vertex and
        // the other vertex based on their x and y positions

        // a^2 + b^2
        double dist = Math.pow(other.getXPos() - this.getXPos(), 2)
                    + Math.pow(other.getYPos() - this.getYPos(), 2);

        // take the square root of dist, c^2
        dist = Math.sqrt(dist);

        return dist;
    }


    public void connect(Vertex other){
        // updates this vertex' adjacency list/map so that it
        // connects with the vertex at direction dirn

        // if other isn't already in the adjacent list
        if (!this.adjacents.contains(other)){
            // add it to the adjacent list
            this.adjacents.add(other);
        }
    }


    public void connect(Vertex other, Direction dirn){
        // updates this vertex' adjacency list/map so that it
        // connects with the vertex at direction dirn

        if (dirn == Direction.NORTH){
            // add the neighbor at the same x and at y - 1
            this.adjacents.set(0, other);
        }
        else if (dirn == Direction.EAST){
            // add the neighbor at x + 1 and the same y
            this.adjacents.set(1, other);
        }
        else if (dirn == Direction.SOUTH){
            // add the neighbor at x and y + 1
            this.adjacents.set(2, other);
        }
        else {// dirn == Direction.WEST
            // add the neighbor at x - 1 and y
            this.adjacents.set(3, other);
        }
    }


    public String toString(){
        // returns a string containing num neighbors, vertex's cost, marked flag

        // declare a new String object
        String result = "";

        result += "Number of neighbors: " + this.numNeighbors() + "\n";
        result += "Vertex distance from root: " + this.getDistanceFromRoot() + "\n";
        result += "Visited: " + this.getVisited() + "\n";

        return result;
    }


    public int compareTo(Vertex other){
        // compares this vertex to another one

        return (int)(this.getDistanceFromRoot() - other.getDistanceFromRoot());
    }


    public static boolean matchPosition(Vertex a, Vertex b){
        // returns true if the two vectors have the same position
        if (a.getXPos() == b.getXPos() && a.getYPos() == b.getYPos()){
            return true;
        }

        return false;
    }


    public void draw(Graphics g, int scale) {
        // draw method used in the draw method in Landscape class

        if (!this.visible)
            return;
        int xpos = (int)this.getXPos()*scale;
        int ypos = (int)this.getYPos()*scale;
        int border = 2;
        int half = scale / 2;
        int eighth = scale / 8;
        int sixteenth = scale / 16;
        
        // draw rectangle for the walls of the room
        if (this.getDistanceFromRoot() <= 2)
            // wumpus is nearby
            g.setColor(Color.red);
        else
            // wumpus is not nearby
            g.setColor(Color.black);
        
        g.drawRect(xpos + border, ypos + border, scale - 2*border, scale - 2 * border);
        
        // draw doorways as boxes
        g.setColor(Color.black);
        if (this.getNeighbor( this.getXPos(), this.getYPos()-1 ) != null )
            g.fillRect(xpos + half - sixteenth, ypos, eighth, eighth + sixteenth);
        if (this.getNeighbor( this.getXPos(), this.getYPos()+1 ) != null )
            g.fillRect(xpos + half - sixteenth, ypos + scale - (eighth + sixteenth), 
                       eighth, eighth + sixteenth);
        if (this.getNeighbor( this.getXPos()-1, this.getYPos() ) != null)
            g.fillRect(xpos, ypos + half - sixteenth, eighth + sixteenth, eighth);
        if (this.getNeighbor( this.getXPos()+1, this.getYPos() ) != null)
            g.fillRect(xpos + scale - (eighth + sixteenth), ypos + half - sixteenth, 
                       eighth + sixteenth, eighth);
    }


    // main method containing test code
    public static void main(String[] args){

        Vertex uno = new Vertex();

        Vertex dos = new Vertex();

        Vertex quatro = new Vertex();

        System.out.println(uno.toString());
    }
}