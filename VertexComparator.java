/*
Milo Lani-Caputo
12/21/21
VertexComparator.java
Hunt the Wumpus
*/

import java.util.Comparator;

/*
This class is a comparator class that is used to compare two vertices' distance from the root vertex.
Used in the Graph class's shortest path method, which implements Dijkstra's shortest path algorithm.
*/

public class VertexComparator implements Comparator<Vertex>{

    public VertexComparator(){
        // empty constructor method
    }
    

    public int compare(Vertex v1, Vertex v2){
        // compare method

        double d1 = v1.getDistanceFromRoot();
        double d2 = v2.getDistanceFromRoot();

        if (d1 > d2){
            return 1;
        }
        else if (d2 > d1){
            return -1;
        }
        else {
            return 0;
        }
    }


    public static void main(String[] args){

        VertexComparator obj = new VertexComparator();

        Vertex a = new Vertex(0, 0);
        a.setDistanceFromRoot(10);
        Vertex b = new Vertex(1, 0);
        b.setDistanceFromRoot(20);
        Vertex c = new Vertex(0, 1);
        c.setDistanceFromRoot(30);

        System.out.println(obj.compare(a,b));
        System.out.println(obj.compare(b,a));
        System.out.println(obj.compare(a,c));
        System.out.println(obj.compare(a,a));
    }
}