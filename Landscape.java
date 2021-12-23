/*
Milo Lani-Caputo
12/21/21
Landscape.java
Hunt the Wumpus
*/

import java.util.ArrayList;
import java.awt.Graphics;

/*
Landscape class, holds a list of vertices and other objects to be displayed in the game.
*/

public class Landscape{

    int width;
    int height;

    Hunter hunt;
    Wumpus wump;
    LinkedList<Vertex> list;

    public Landscape(int w, int h){
        // constructor method, sets width and heights and initializes list
        this.width = w;
        this.height = h;
        this.list = new LinkedList<Vertex>();
        this.hunt = new Hunter();
        this.wump = new Wumpus();
    }

    public Landscape(int w, int h, ArrayList<Vertex> arr){
        // constructor method, sets width and heights and initializes list
        this.width = w;
        this.height = h;
        this.list = new LinkedList<Vertex>();
        this.hunt = new Hunter();
        this.wump = new Wumpus();

        // populate the LinkedList with the contents of the ArrayList
        for (Vertex v : arr){
            this.list.addFirst(v);
        }
    }

    public int getHeight(){
        // returns the height of the landscape
        return this.height;
    }

    public int getWidth(){
        // returns the width of the landscape
        return this.width;
    }

    public void addVertex(Vertex v){
        // inserts a vertex at the beginning of its list of vertex
        this.list.addFirst(v);
    }

    public String toString(){
        // returns a String representing the Landscape
        String result = "number of agents on Landscape: " + this.list.size();

        return result;
    }


    public void draw(Graphics g, int scale){
        // calls the draw method of all the agents on the Landscape

        // iterate through the ArrayList of vertices
        for (Vertex v : this.list){
            // call the draw method on the Graphics object g
            v.draw(g, scale);// hard-coded scale of 64*******************
        }

        // draw the hunter
        this.hunt.draw(g, scale);
        
        // draw the wumpus
        this.wump.draw(g, scale);
    }


    // main method containing test code
    public static void main(String[] args){

        // Landscape l = new Landscape(500, 500);

        // System.out.println(l.getHeight());
        // System.out.println(l.getWidth());

        // l.addAgent(new Agent(0.0,0.0));
        // l.addAgent(new Agent(1.5,9.3));
        // l.addAgent(new Agent(1.0,1.0));

        // System.out.println(l.toString());

        // System.out.println(l.getNeighbors(0.5,0.5,1.0));
    }
}