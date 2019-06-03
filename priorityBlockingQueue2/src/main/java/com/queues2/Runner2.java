package com.queues2;


import java.util.concurrent.PriorityBlockingQueue;

/**
 * Detect the time taken to save an object
 */
@SuppressWarnings("ALL")
public class Runner2 {
	public static void main(String[] args) throws InterruptedException {
		//Declare a queue. This is an unbounded queue, so be careful when using it
		PriorityBlockingQueue<User> priorityBlockingQueue = new PriorityBlockingQueue<User>();
		int objSize=100; 							//Defines the initial size of the object
		int[] objSizes=new int[]{1,200,500,1000,2000,5000,10000,20000,50000};	//Current store object size
		for(int j=0;j<objSizes.length;j++){
			long start = System.currentTimeMillis();	//start time
			for(int i=0;i<objSizes[j];i++){				//save value
				int age=(int)(1+Math.random()*(100));
				int unum=(int)(1+Math.random()*(5));
				if(unum==1){
					priorityBlockingQueue.put(new User("miker"+i,age));
				}else if(unum==2){
					priorityBlockingQueue.put(new User("Tina"+i,age));
				}else if(unum==3){
					priorityBlockingQueue.put(new User("Griselda"+i,age));
				}else if(unum==4){
					priorityBlockingQueue.put(new User("Elisabeth"+i,age));
				}else  {
					priorityBlockingQueue.put(new User("Charley"+i,age));
				}
			}
			long end = System.currentTimeMillis();	//end time
			long res= end-start; //result
			System.out.println("object size"+objSizes[j]+"，Time consuming to load data into queue："+res);
		}

		while(true){
			User user = priorityBlockingQueue.take();
			System.out.println(user.getName()+","+user.getAge()); //Print out user object information from smallest to largest by age
		}
	}
}