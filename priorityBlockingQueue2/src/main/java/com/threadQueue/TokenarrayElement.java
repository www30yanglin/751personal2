package com.threadQueue;

import java.util.Comparator;
import java.util.concurrent.locks.ReentrantLock;


public class TokenarrayElement<E extends Comparable<E>>  {

	private E value;// The value of element
    
	private int position;// The index of element in binary heap
	
	private final ReentrantLock tokenarrayLock;//The lock that control the concurrent access in the Tokenarray
	
	private Comparator<? super E> comparator;// For different comparing strategies
	
	/*
	 * constructor
	 * 
	 * @param value                The value of the element 
	 * @param position             The index of the element in binary heap
	 * @param tokenarrayLock       The lock that control the concurrent access in the Tokenarray 
	 * 
	 */

	 public TokenarrayElement(E value, int position, ReentrantLock tokenarrayLock, Comparator<? super E> comparator) {
             this.value = value;
             this.position = position;
             this.tokenarrayLock = tokenarrayLock;
     }
	 
	 
	 /*
	  * The methods about value
	  */
	 public E getValue()
	 {
		 return value;
	 }
	 
	 public void setValue(E value)
	 {
		 this.value =value;
	 }
	 
	 
	 /*
	  * The methods about position
	  */
	 public int getPosition()
	 {
		 return this.position;
	 }
	 
	 public void setPosition(int position)
	 {
		 this.position =position;
	 }
	 
	 
	 /*
	  * The methods about lock
	  */
	 public void lock()
	 {
		 this.tokenarrayLock.lock();
	 }
	 
	 public void unlock()
	 {
		 this.tokenarrayLock.lock();
	 }
	 
	 public ReentrantLock getLock()
	 {
		 return this.tokenarrayLock;
	 }
	 
	 
	 /*
	  * 
	  * Compare the values of two different elements
	  * if the first one is greater, return true
	  * 
	  */
	 
	 public boolean isGreaterThan(E e) {
		 int res;
		if(this.comparator == null)
	       res= this.value.compareTo(e);
		else
		   res= comparator.compare(e, value);
	        return (res > 0);	
	}



}
