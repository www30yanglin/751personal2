package com.queues2;


import java.util.concurrent.PriorityBlockingQueue;

/***
 *change the order of peek
 */
@SuppressWarnings("ALL")
public class Runner3 {
	public static void main(String[] args) throws InterruptedException {
		//this is an unbounded queue 
		PriorityBlockingQueue<User> priorityBlockingQueue = new PriorityBlockingQueue<User>();
		int objSize=100; //define initial size of objects
		for(int i=0;i<objSize;i++){
			 int age=(int)(1+Math.random()*(100));
			int unum=(int)(1+Math.random()*(5));
			if(unum==1){
				priorityBlockingQueue.put(new User("aa"+i,age));
			}else if(unum==2){
				priorityBlockingQueue.put(new User("bb"+i,age));
			}else if(unum==3){
				priorityBlockingQueue.put(new User("cc"+i,age));
			}else if(unum==4){
				priorityBlockingQueue.put(new User("dd"+i,age));
			}else  {
				priorityBlockingQueue.put(new User("ee"+i,age));
			}
		}
		while(true){
			User user = priorityBlockingQueue.take();
			System.out.println(user.getName()+","+user.getAge()); ////print user information in ascending order with ages
		}
	}
}