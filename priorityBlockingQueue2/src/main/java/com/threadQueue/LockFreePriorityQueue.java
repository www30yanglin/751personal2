package com.threadQueue;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;


/**
 * This is a lock free priority queue for multi-threads. 
 * It is based on the algorithm in
 * Fast and Lock-Free Concurrent Priority Queues for Multi-Thread Systems By
 * Philippas Tsigas and Hakan Sundell.
 * Because it is lock free, so it would not cause dead lock and starvation
 * 
 * @author Dong
 * 
 */

public class LockFreePriorityQueue<E> extends AbstractQueue<E>
{

    /**
     * Initial size
     */
    static final int MAXLEVEL = 10; 

    
    /**
     * Used to create random level
     */
    static final double SLCONST = 0.50; 

    
    /**
     * Head pointer.
     */
    LockFreeNode<E> head = new LockFreeNode<E>();

    
    /**
     * Tail pointer.
     */
    LockFreeNode<E> tail = new LockFreeNode<E>(); 

    
   /**
     * Random number generator.
     */
    static final Random RAND_GEN = new Random();

    
    /**
     * Use comparator for threads_safety
     */
    private final Comparator<? super E> comparator;
    

    
    /**
     * Pair of internal node.
     * 
     * @param <E>
     *            type of element in node
     */
    private static class NodePair<E> {
    	LockFreeNode<E> n1; 
    	LockFreeNode<E> n2;

        /**
         * @param nn1
         *            the first node
         * @param nn2
         *            the second node
         */
        public NodePair(LockFreeNode<E> nn1, LockFreeNode<E> nn2) {
            n1 = nn1;
            n2 = nn2;
        }
    }

    
    
    /**
     * Iterator definition of priority queue.
     */
    private class Itr implements Iterator<E> {

    	LockFreeNode<E> cursor = head.next.get(0).getReference();

    	
        /**
         * Check if there is a node next to it
         */
        public boolean hasNext() {
            return cursor != tail;
        }

        
        /**
         * Get the next element
         */
        public E next() {
            if (cursor == tail)
                throw new NoSuchElementException();

            E result = cursor.data.getReference();
            cursor = cursor.next.get(0).getReference();
            return result;
        }

        
        /**
         * @throws UnsupportedOperationException every time because this class does not support this operation
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }




    /**
     * default constructor.
     */
    public LockFreePriorityQueue() {
        for (int i = 0; i < MAXLEVEL; i++) {
            head.next.add(new AtomicMarkableReference<LockFreeNode<E>>(tail, false));
        }

        comparator = null;
    }

    
    
    /**
     * @param initialCapacity
     * constructor
     */  
    public LockFreePriorityQueue(int initialCapacity) 
    {
        this(initialCapacity, null);
    }
 
    

    /**
     * @param initialCapacity, Comparator
     * constructor
     */  
    public LockFreePriorityQueue(int initialCapacity, Comparator<? super E> cmp)
    {
		if (initialCapacity < 1)
		throw new IllegalArgumentException();
        for (int i = 0; i < initialCapacity; i++) {
            head.next.add(new AtomicMarkableReference<LockFreeNode<E>>(tail, false));
        }
		this.comparator = cmp;
	}
    
 
    
    /**
     * @param A collection of elements
     * constructor
     */  
    public LockFreePriorityQueue(Collection<? extends E> c) {
        if (c == null) throw new IllegalArgumentException("Input collection cannot be null");
        //addAll(c);
        for (int i = 0; i < MAXLEVEL; i++) {
            head.next.add(new AtomicMarkableReference<LockFreeNode<E>>(tail, false));
        }
        this.comparator = null;
    }

	
    
    /**
     * Get the size of the queue
     */
    public int size() {
        int size = 0;
        Iterator<E> itr = iterator();
        while (itr.hasNext()) {
            itr.next();
            size++;
        }
        return size;
    }
    
    

    /**
     * Retrieves, but does not remove, the head of this queue,or returns null if this queue is empty.
     */
    public E peek() {
        return head.next.get(0).getReference().data.getReference();
    }

   
    
    /**
     * @param The element which is going to be added to the queue
     * Inserts the specified element into this queue if it is possible to do so immediately without violating capacity restriction
     */
    public boolean offer(E e) {
    	if (e == null)
			throw new NullPointerException();
        return add(e);
    }

    
    
    /**
     * @param The element which is going to be added to the queue, timeout and TimeUnit can be anything here because it is lock free
     * Inserts the specified element into this queue if it is possible to do so immediately without violating capacity restriction
     */    
    public boolean offer(E e, long timeout, TimeUnit unit) {
        return offer(e); // never need to block
    }
   
    
    
    /**
     * Return the first element
     */
    public E peekFirst() {
        return head.next.get(0).getReference().data.getReference();
    }
    
   
    
    /**
     * Always returns {@code Integer.MAX_VALUE} because
     * a {@code PriorityBlockingQueue} is not capacity constrained.
     * @return {@code Integer.MAX_VALUE} always
     */
    public int remainingCapacity() {
        return Integer.MAX_VALUE;
    }

    
    
    /**
     * Retrieves and removes the head of this queue,or returns null if this queue is empty.
     */
    public E poll() {
        return deleteMin();
    }
    
    
    
    /**
     * Retrieves and removes the head of this queue,or returns null if this queue is empty.
     */
    public void put(E e) {
        offer(e); // never need to block
    }

    
    
    /**
     * Returns true if this collection contains the specified element.More formally, returns true if and only if this collection
     * contains at least one element e such that Objects.equals(o, e).
     */
    public boolean contains(Object o) {
        E element;
        Iterator<E> itr = new Itr();
        while (itr.hasNext()) {
            element = itr.next();
            if (element.equals(o)) {
                return true;
            }
        }
        return false;
    }
    


    /**
     * To check if the queue is empty
     */
    public boolean isEmpty() {
        return head.next.get(0).getReference() == tail;
    }

    
    
    /**
     * Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics. 
     * Method names have been improved. 
     */
    public Iterator<E> iterator() {
        return new Itr();
    }

    
    
    /**
     * @param k1
     *            the first element
     * @param k2
     *            the second element
     * @return -1 if k1 is less than k2; 0 if k1 equals k2; otherwise 1
     */
    private int compare(E k1, E k2) {

        if ((k1 == null) && (k2 == null))
            return 0;
        if (k1 == null)
            return -1;
        else if (k2 == null)
            return 1;
        else {
            if (comparator == null)
                return ((Comparable<? super E>) k1).compareTo(k2);
            else
                return comparator.compare(k1, k2);
        }

    }

    
    
    /**
     * Get the reference of the Node in that position. 
     * If it has been marked, return null
     */
    private LockFreeNode<E> readnode(LockFreeNode<E> n, int ll) {

        if (n.next.get(ll).isMarked())
            return null;
        else
            return n.next.get(ll).getReference();
    }
    
    
    
    /**
     * Get the reference of the next Node and return the node pair
     */
    private NodePair<E> readNext(LockFreeNode<E> n1, int ll) {

    	LockFreeNode<E> n2, nn2;

        if (n1.data.isMarked()) {
            nn2 = helpDelete(n1, ll);
            n1 = nn2;
        }

        n2 = readnode(n1, ll);
        while (n2 == null) {
            nn2 = helpDelete(n1, ll);
            n1 = nn2;
            n2 = readnode(n1, ll);
        }

        return new NodePair<E>(n1, n2);
    }

    
    /**
     * Return the node pair if it exists in the queue
     */
    private NodePair<E> scanKey(LockFreeNode<E> n1, int ll, LockFreeNode<E> nk) {

    	LockFreeNode<E> n2;
        NodePair<E> tn1, tn2;

        tn1 = readNext(n1, ll);
        n1 = tn1.n1;
        n2 = tn1.n2;

        while ((compare(n2.data.getReference(), nk.data.getReference()) < 0)
                && (n2 != tail)) {
            n1 = n2;
            tn2 = readNext(n1, ll);
            n1 = tn2.n1;
            n2 = tn2.n2;
        }

        return new NodePair<E>(n1, n2);
    }

    
    
    /**
     * @return prev element
     */
    private LockFreeNode<E> helpDelete(LockFreeNode<E> n, int ll) {

        int i = 0;
        LockFreeNode<E> prev, last, n2;
        NodePair<E> tn1, tn2;
        AtomicMarkableReference<LockFreeNode<E>> tempn1;

        // Mark all the next pointers of the node to be deleted
        for (i = ll; i <= n.validLevel - 1; i++) {
            do {
                tempn1 = n.next.get(i);
                n2 = tempn1.getReference();
            } while (!tempn1.compareAndSet(n2, n2, false, true)
                    && !tempn1.isMarked());
        }

        // Get the previous pointer
        prev = n.prev;
        if ((prev == null) || (ll >= prev.validLevel)) {
            prev = head;
            for (i = MAXLEVEL - 1; i >= ll; i--) {
                tn1 = scanKey(prev, i, n);
                n2 = tn1.n2;
                prev = tn1.n1;
            }
        }

        LockFreeNode<E> tmpN = n.next.get(ll).getReference();

        while (true) {
            if (n.next.get(ll).getReference() == null)
                break;
            tn2 = scanKey(prev, ll, n);
            last = tn2.n2;
            prev = tn2.n1;
            if (((last != n) || (n.next.get(ll).getReference() == null)))
                break;

            if (!tmpN.data.isMarked()) {
                if (prev.next.get(ll).compareAndSet(n, tmpN, false, false)) {
                    n.next.get(ll).set(null, true);
                    break;
                }
            } else
                tmpN = tmpN.next.get(ll).getReference();

            if (n.next.get(ll).getReference() == null)
                break;
        }

        return prev;
    }

    
    
    /**
     * Create a random level
     */
    private int randomLevel() {
        int v = 1;

        while ((RAND_GEN.nextDouble() < SLCONST) && (v < MAXLEVEL - 1)) {
            v = v + 1;
        }

        return v;
    }

    
    
    /**
     * @param The element which is going to be added to the queue
     * Inserts the specified element into this queue if it is possible to do so immediately without violating capacity restriction
     */
    public boolean add(E d) {
        int level, i;

        LockFreeNode<E> newN;
        LockFreeNode<E> n1, n2;
        ArrayList<LockFreeNode<E>> savedNode = new ArrayList<LockFreeNode<E>>();
        NodePair<E> tn1, tn2, tn3;

        for (int ii = 0; ii < MAXLEVEL; ii++) {
            savedNode.add(new LockFreeNode<E>());
        }

        level = randomLevel();
        newN = new LockFreeNode<E>(level, d);

        n1 = head;

        for (i = MAXLEVEL - 1; i >= 1; i--) {
            tn1 = scanKey(n1, i, newN);
            n2 = tn1.n2;
            n1 = tn1.n1;
            if (i < level) {
                savedNode.set(i, n1);
            }
        }

        int kk = 0;
        while (true) {
            tn2 = scanKey(n1, 0, newN);
            n2 = tn2.n2;
            n1 = tn2.n1;

            if ((compare(d, n2.data.getReference()) == 0)
                    && (!n2.data.isMarked())) {
                if (n2.data.compareAndSet(n2.data.getReference(), d, false,
                        false)) {
                    return true;
                } else {
                    continue;
                }
            }

            if (kk == 0) {
                newN.next.add(new AtomicMarkableReference<LockFreeNode<E>>(n2, false));
                kk++;
                newN.validLevel = 0;
            } else {
                newN.next.set(0,
                        new AtomicMarkableReference<LockFreeNode<E>>(n2, false));
            }
            if (n1.next.get(0).compareAndSet(n2, newN, false, false)) {
                break;
            }
        }

        for (i = 1; i <= level - 1; i++) {
            newN.validLevel = i;
            n1 = savedNode.get(i);
            kk = 0;
            while (true) {

                tn3 = scanKey(n1, i, newN);
                n2 = tn3.n2;
                n1 = tn3.n1;

                if (kk == 0) {
                    newN.next.add(new AtomicMarkableReference<LockFreeNode<E>>(n2,
                            false));
                    kk++;
                } else {
                    newN.next.set(i, new AtomicMarkableReference<LockFreeNode<E>>(n2,
                            false));
                }
                if (newN.data.isMarked())
                    break;
                if (n1.next.get(i).compareAndSet(n2, newN, false, false)) {
                    break;
                }
            }
        }

        newN.validLevel = level;

        if (newN.data.isMarked())
            newN = helpDelete(newN, 0);

        return true;
    }

    
    
    /**
     * @return min element
     */
    private E deleteMin() {
    	LockFreeNode<E> n2, last, prev;
    	LockFreeNode<E> n1 = null;
        E val;
        int i = 0;
        NodePair<E> tn1, tn2;
        AtomicMarkableReference<LockFreeNode<E>> tempn1;
        int iflag = 0;

        prev = head;

        retry: while (true) {
           
            if (iflag != 1) {
                tn1 = readNext(prev, 0);
                n1 = tn1.n2;
                prev = tn1.n1;
                if (n1 == tail)
                    return null;
            }
            iflag = 0;

            val = n1.data.getReference();
            if (!n1.data.isMarked()) {
                if (n1.data.compareAndSet(n1.data.getReference(), n1.data
                        .getReference(), false, true)) {
                    n1.prev = prev;
                    break;
                } else {
                    iflag = 1;
                    continue retry;
                }
            } else if (n1.data.isMarked()) {
                n1 = helpDelete(n1, 0);
            }
            prev = n1;
        }
        for (i = 0; i <= n1.validLevel - 1; i++) {
            do {
                tempn1 = n1.next.get(i);
                n2 = tempn1.getReference();
            } while (!tempn1.compareAndSet(n2, n2, false, true)
                    && !tempn1.isMarked()); // REVIEW different from paper
        }

        prev = head;

        for (i = n1.validLevel - 1; i >= 0; i--) {
        	LockFreeNode<E> tmpN = n1.next.get(i).getReference();
            while (true) {
                if (n1.next.get(i).getReference() == null)
                    break;
                tn2 = scanKey(prev, i, n1);
                last = tn2.n2;
                prev = tn2.n1;

                if (((last != n1) || (n1.next.get(i).getReference() == null)))
                    break;

                if (!tmpN.data.isMarked()) {
                    if (prev.next.get(i).compareAndSet(n1, tmpN, false, false)) {
                        n1.next.get(i).set(null, true);
                        break;
                    }
                } else
                    tmpN = tmpN.next.get(i).getReference();

                if (n1.next.get(i).getReference() == null)
                    break;
            }

        }

        return val;
    }

}