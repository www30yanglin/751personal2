package com.threadQueue;

import java.util.Comparator;

public class BinaryheapNode<E extends Comparable<E>> {
	 private E value;// The value of this node
	
	 private boolean isActive;//The state of this node
	    
	 private int capacity;//The number of child nodes available
	 
	 private Comparator<? super E> comparator;// For different comparing strategies
	 
	 public BinaryheapNode(E value, boolean isActive, int capacity,Comparator<? super E> comparator)
	 {
		 this.value=value;
		 this.isActive=isActive;
		 this.capacity=capacity;
	 }
	 
	 
	 /*  
	  * Methods about value
	  */
	 
	 public void setValue(E value)
	 {
		 this.value=value;
	 }
	 
	 public  E getValue()
	 {
		 return this.value;
	 }
	 
	 
	 /*
	  * Methods about isActive 
	  */
	 public void setActive(boolean isActive)
	 {
		this.isActive=isActive; 	 
     }
	 
	 public boolean getActive()
	 {
		 return this.isActive;
	 }
	 
	 
	 /*
	  * Methods about capacity
	  */
	 public void setCapacity(int capacity)
	 {
		 this.capacity=capacity;
	 }
	 
	 public int getCapacity()
	 {
		 return this.capacity;
	 }
	 
	 public void decCap()
	 {
		 this.capacity--;
	 }
	 
	 public void incCap()
	 {
		 this.capacity++;
	 }
	 
	 /*
	  * 
	  * Compare the values of two different nodes
	  * if the first one is greater, return true
	  * 
	  */
	 
	 public boolean isGreaterThan(E e) {
		 int res;
		if(this.comparator ==null)
	       res= this.value.compareTo(e);
		else
		   res= comparator.compare(e, value);
	        return (res > 0);	
	}
}
