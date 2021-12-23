/*
Milo Lani-Caputo
12/21/21
LinkedList.java
Hunt the Wumpus
*/

import java.util.Iterator;    // defines the Iterator interface
import java.util.ArrayList;   
import java.util.Collections; // contains a shuffle function

/*
This class provides a from-scratch implementation of a LinkedList data structure.
*/

public class LinkedList<T> implements Iterable<T>{

    // private Node class
    private class Node<T>{
        // fields for data and next
        Node<T> next;
        T data;


        // constructor function
        public Node(T item){
            this.next = null;
            this.data = item;
        }


        // accessor, returns the data of the node
        public T getThing(){
            return this.data;
        }


        // modifier, sets the next value of the node
        public void setNext(Node<T> n){
            this.next = n;
        }


        // accessor, returns the next value of the node
        public Node<T> getNext(){
            return this.next;
        }
    }


    private class LLIterator implements Iterator<T>{
        // iterator class
        // field for the next node the iterator is on
        Node<T> nextNode;


        public LLIterator(Node<T> head){
            // constructor method for the iterator
            this.nextNode = head;
        }


        public boolean hasNext(){
            /* returns true if there are still values to traverse
            (if the current node reference is not null). */
            if (this.nextNode != null){
                return true;
            } else {
                return false;
            }
        }


        public T next(){
            /* returns the next item in the list, 
            which is the item contained in the current node. 
            The method also moves the traversal along to the next node in the list. */
            
            // temporary variable to hold the thing to be returned
            T tempData = this.nextNode.getThing();
            // moves the iterator to the next node in the list
            this.nextNode = this.nextNode.getNext();
            // return the thing
            return tempData;
        }
    }


    // LinkedList fields
    Node<T> head;
    int size;


    // constructor method
    public LinkedList(){
        this.head = null;
        this.size = 0;
    }


    public void clear(){
        // resets the fields, clearing the list
        this.head = null;
        this.size = 0;
    }


    public int size(){
        // returns the size of the list
        return this.size;
    }


    public void addFirst(T item){
       //inserts the item at the beginning of the list.
       Node<T> newNode = new Node<T>(item);

       newNode.setNext(this.head);
       this.head = newNode;

       this.size++;
    }


    public Iterator<T> iterator() {
        // Return a new LLIterator pointing to the head of the list
        return new LLIterator(this.head);
    }


    public String toString(){
        String result = "";

        // create an iterator object
        Iterator<T> iter = iterator();

        while (iter.hasNext()){
            // while the iterator aren't at the end of the linked list
            // increment, setting the temp reference to the current Node
            result += iter.next() + ", ";
        }

        return result;
    }


    public void addLast(T item){
        // appends the item at the end of the list.

        // create a node reference and set it to reference the head
        Node<T> temp = this.head;

        // if head is not empty
        if (temp != null){
            // while we are not on the last item of the list
            while (temp.getNext() != null){
                // move the reference to the next node
                temp = temp.getNext();
            }
            
            // set the next node to a new node of value item
            temp.setNext(new Node<T>(item));

            // increase size by one
            this.size++;

        } else {
            // if the linked list is empty
            // just add to the beginning cause it's the same difference!
            this.addFirst(item);
            // System.out.println("added first in last");
        }
    }


    public void add(int index, T item){
        // inserts the item at the specified position in the list

        // create a node reference and set it to reference the head
        Node<T> temp = this.head;

        // if head is not empty (empty linked list) AND index is not 0 (addFirst)
        if (temp != null && index != 0){
            // iterate through the linked list index times
            for (int i = 0; i < index - 1; i++){
                // move the reference to the next node
                temp = temp.getNext();
            }
            
            // create a new node to hold the item
            Node<T> newNode = new Node<T>(item);

            // set the newNode's next to temp's next
            newNode.setNext(temp.getNext());            

            // set temp's next node to newNode
            temp.setNext(newNode);

            // increase size by one
            this.size++;

        } else {
            // if the linked list is empty or the index to be added at is 0
            // just add to the beginning cause it's the same difference!
            this.addFirst(item);
        }
    }


    public T remove(int index){
        // removes the item at the specified position in the list

        // create a node reference and set it to reference the head
        Node<T> temp = this.head;

        // if removing the first node and linked list isn't empty
        if (index == 0 && temp != null){
            // set the head to the next item
            this.head = temp.getNext();
        }
        // if head is not null (empty linked list)
        else if (temp != null){

            /* iterate through the linked list until you get to the node
            previous to the one at index */
            for (int i = 0; i < index - 1; i++){
                // move the reference to the next node
                temp = temp.getNext();
            }

            // create another temporary node reference to the desired index node
            Node<T> temp2 = temp.getNext();

            // set the previous node's next reference to the next node
            temp.setNext(temp2.getNext());
            
            // set temp's reference to reference temp2
            temp = temp2;

            // remove temp's next reference so it will be deleted from memory
            temp.setNext(null);
        }

        return temp.getThing();
    }


    public ArrayList<T> toArrayList(){
        // returns an ArrayList of the list contents in order

        // create an ArrayList to return
        ArrayList<T> arr = new ArrayList<T>();

        // create an iterator object
        Iterator<T> iter = iterator();

        // while the iterator isn't at the end of the linked list
        while (iter.hasNext()){
            // add each new item to the ArrayList
            arr.add(iter.next());
        }

        return arr;
    }


    public ArrayList<T> toShuffledList(){
        // returns a shuffled ArrayList of the list contents in order

        // create an ArrayList to return
        ArrayList<T> arr = new ArrayList<T>();

        // create an iterator object
        Iterator<T> iter = iterator();

        // while the iterator isn't at the end of the linked list
        while (iter.hasNext()){
            // add each new item to the ArrayList
            arr.add(iter.next());
        }

        Collections.shuffle(arr);

        return arr;
    }


    // main method containing test code
    public static void main(String[] args) {
		
		LinkedList<Integer> lList = new LinkedList<Integer>();

		System.out.println("Length of Linked List: " + lList.size());
        System.out.println(lList.toString());

        for (int i = 0; i < 10; i++){
            lList.addFirst(i);
        }

        System.out.println("Length of Linked List: " + lList.size());
        System.out.println(lList.toString());


        lList.clear();
        System.out.println("Length of Linked List: " + lList.size());
        System.out.println(lList.toString());

        lList.addLast(89);
        lList.addLast(90);
        lList.addLast(91);
        lList.addFirst(8);
        System.out.println(lList.toString());

        lList.add(1,66);
        lList.add(0,10);
        lList.add(4,45);
        lList.add(7,33);
        System.out.println("Length of Linked List: " + lList.size());
        System.out.println(lList.toString());

        System.out.println("removing item at index 0 (10): " + lList.remove(0));
        System.out.println("removing item at index 4 (90): " + lList.remove(4));
        System.out.println("removing item at index 5 (33): " + lList.remove(5));
	}
}