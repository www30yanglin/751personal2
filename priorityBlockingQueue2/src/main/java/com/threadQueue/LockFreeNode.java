package com.threadQueue;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicMarkableReference;


/**
 * This is the LockFreeNode class for lock free priority queue.
 * A single instance of LockFreeNode<E> represents a basic element in used for the LockFreePriorityQueue.
 * 
 * @author Dong
 * 
 */

public class LockFreeNode<E> 
{
	public int level, validLevel;
    public AtomicMarkableReference<E> data;
    public LockFreeNode<E> prev;
    public ArrayList<AtomicMarkableReference<LockFreeNode<E>>> next;
  
    /**
     * Default constructor.
     */
    public LockFreeNode() {
        this.data = new AtomicMarkableReference<E>(null, false);
        this.prev = null;
        this.next = new ArrayList<AtomicMarkableReference<LockFreeNode<E>>>(
                MAXLEVEL);
        this.validLevel = -1;
    }

    /**
     * @param Random level and a initialRef for AtomicMarkableReference
     * constructor
     */  
    public LockFreeNode(int l, E d) {
        this.data = new AtomicMarkableReference<E>(d, false);
        this.next = new ArrayList<AtomicMarkableReference<LockFreeNode<E>>>(
                MAXLEVEL);
        this.level = l;

    }

    
    
    /**
     * @param Random level, initialRef for AtomicMarkableReference and a next node
     * constructor
     */  
    public LockFreeNode(int l, E d, ArrayList<AtomicMarkableReference<LockFreeNode<E>>> al) {
        this.level = l;
        this.data = new AtomicMarkableReference<E>(d, false);
        this.next = al;
    }

    
    
    /**
     * @param prev pointer
     * Set previous Node
     */
    public void setPrev(LockFreeNode<E> p) {
        this.prev = p;
    }

    static final int MAXLEVEL = 10;
}
