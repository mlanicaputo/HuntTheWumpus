/*
Milo Lani-Caputo
12/21/21
Graph.java
Hunt the Wumpus
*/

import java.util.ArrayList;
import java.util.PriorityQueue;

/*
This class implements a Graph data structure from scratch, building on the Vertex class.
Includes a shortestPath method which implements Dijkstra's shortest path algorithm.
*/

public class Graph{

    private ArrayList<Vertex> list;


    public Graph(){
        // constructor method

        // initialize the ArrayList field with an empty list of size numVertices
        this.list = new ArrayList<Vertex>();
    }

    public Graph(int numVertices){
        // constructor method
        
        // initialize the ArrayList field with an empty list of size numVertices
        this.list = new ArrayList<Vertex>(numVertices);

        // populate the list with vertices
        for (int i = 0; i < numVertices; i++){
            // add a vertex
            this.list.add(new Vertex());
        }
    }


    public Vertex get(int index){
        // returns the vertex at the index in the field
        return this.list.get(index);
    }


    public int vertexCount(){
        // returns the number of vertices in the graph
        return this.list.size();
    }


    public boolean inGraph(Vertex query){
        // return true if the query Vertex is in the graph's vertex list
        if (this.list.contains(query)){
            return true;
        }

        return false;
    }


    public void addUniEdge(Vertex v1, Vertex v2){
        // adds v1 and v2 to the graph (if necessary) 
        // and adds an edge connecting v1 to v2, creating a uni-directional link

        // if v1 isn't in the graph
        if (!this.list.contains(v1)){
            // add the vertex to the graph
            this.list.add(v1);
        }
        // if v2 isn't in the graph
        else if (!this.list.contains(v2)){
            // add the vertex to the graph
            this.list.add(v2);
        }

        // add an edge connecting v1 to v2 in v1's direction dirn
        v1.connect(v2);
    }


    public void addBiEdge(Vertex v1, Vertex v2){
        //adds v1 and v2 to the graph (if necessary), adds an edge connecting 
        // v1 to v2, and adds a second edge connecting v2 to v1,
        // creating a bi-directional link.

        // if v1 isn't in the graph
        if (this.inGraph(v1) == false){
            // add the vertex to the graph
            this.list.add(v1);
        }
        // if v2 isn't in the graph
        else if (this.inGraph(v2) == false){
            // add the vertex to the graph
            this.list.add(v2);
        }

        // add an edge connecting v1 to v2 in v1's direction dirn
        v1.connect(v2);
        v2.connect(v1);
    }


    public void shortestPath(Vertex v0){
        // implements a single-source shortest-path algorithm for the Graph, Dijkstra's algorithm
        // sets the distances of all vertices in the graph according to the root vertex parameter

        // Given: a graph G and starting vertex v0 in G
	
        // Initialize all vertices in G to be unmarked, have a large cost, and a null parent
        // (A large cost can be 1e+7)
        for (Vertex v : this.list){
            // make each vertex unmarked, large cost, null parent
            v.setParent(null);
            v.setVisited(false);
            v.setDistanceFromRoot(1e+7);
        }
            
        // Create a priority queue, pq, to hold objects of type Vertex
        PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>(30, new VertexComparator());
            
        // Set the cost of v0 to 0 and add it to pq
        v0.setDistanceFromRoot(0);
        pq.add(v0);
            
        // while q is not empty:
        while (pq.size() > 0){
            // remove v from pq where v is the vertex with lowest cost
            Vertex v = pq.poll();

            // if v is already marked as visited, continue
            if (v.getVisited() == true){
                continue;
            }

            // mark v as visited
            v.setVisited(true);

            // for each vertex w that neighbors v:
            for (Vertex w : v.getAdjacentList()){
                System.out.println("length of adjacent list: " + v.getAdjacentList().size());
                System.out.println("vertex w: " + w.toString());
                // compute the distance between v and w
                double dist = v.distance(w);
                // if w is not marked and v.cost + distance < w.cost:
                if (w.getVisited() == false && v.getDistanceFromRoot() + dist < w.getDistanceFromRoot()){
                    // set w's distance from root to be v's distance from root + dist
                    w.setDistanceFromRoot(v.getDistanceFromRoot() + dist);
                    // make v the parent of w
                    w.setParent(v);
                    // add w to pq
                    pq.add(w);
                }
            }
        }
                        
        // Output: the cost of each vertex v in G is the shortest distance from v0 to v.
        // Each vertex also specifies its parent on the shortest path back to v0 from v.
    }


    public String toString(){
        // returns a string of all the vertices in the graph and their adjacency lists

        // initialize a String to be returned
        String result = "";

        // iterate through the list of vertices
        for (Vertex v : this.list){
            // add the vertex's index to the result
            result += "Vertex " + this.list.indexOf(v) + ": \n";
            // add the result of calling the vector's toString method to result
            result += v.toString();
            // add a line
            result += "--------\n";
        }

        return result;
    }


    // main method, contains test code
    public static void main(String[] args){

        // create a Graph object
        Graph g = new Graph();

        // add coordinates for each vector
        Vertex v0 = new Vertex(0, 0);
        Vertex v1 = new Vertex(1, 0);
        Vertex v2 = new Vertex(3, 2);
        Vertex v3 = new Vertex(1, 3);
        Vertex v4 = new Vertex(0, 2);

        // add edges
        g.addBiEdge(v0, v1);
        g.addBiEdge(v0, v3);

        g.addBiEdge(v1, v2);
        g.addBiEdge(v1, v3);
        g.addBiEdge(v1, v4);

        g.addBiEdge(v2, v4);

        g.addBiEdge(v3, v4);

        // print the graph
        System.out.println("original graph: \n" + g.toString());

        // try out the shortest path algorithm
        g.shortestPath(g.get(3));

        // print the graph again
        System.out.println("distance-updated graph: \n" + g.toString());
    }
}