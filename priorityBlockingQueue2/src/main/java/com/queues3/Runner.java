package com.queues3;

import java.util.concurrent.PriorityBlockingQueue;


/**
 * Queue usage
 */
public class Runner {
	public static void main(String[] args) throws InterruptedException {
		PriorityBlockingQueue<Person> pbq = new PriorityBlockingQueue<>();
		pbq.add(new Person(3,"person3"));
		System.err.println("container for：" + pbq);
		pbq.add(new Person(2,"person2"));
		System.err.println("container for：" + pbq);
		pbq.add(new Person(1,"person1"));
		System.err.println("container for：" + pbq);
		pbq.add(new Person(4,"person4"));
		System.err.println("container for：" + pbq);
		System.err.println("---------After use-------------" );


		System.err.println("Access to elements " + pbq.take().getId());
		System.err.println("container for：" + pbq);
		System.err.println("To distinguish the line----------------------------------------------------------------" );

		System.err.println("Access to elements " + pbq.take().getId());
		System.err.println("container for：" + pbq);
		System.err.println("To distinguish the line----------------------------------------------------------------" );

		System.err.println("Access to elements " + pbq.take().getId());
		System.err.println("container for：" + pbq);
		System.err.println("To distinguish the line----------------------------------------------------------------" );

		System.err.println("Access to elements " + pbq.take().getId());
		System.err.println("container for：" + pbq);
		System.err.println("To distinguish the line----------------------------------------------------------------" );
	}
}
