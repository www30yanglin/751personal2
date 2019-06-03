package com.threadQueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 *
 */
public class QueueContrast implements Runnable,Comparable{
	private int priority=10;
	private String name;
	private int age;

	public QueueContrast(int priority ) {
		this.priority = priority;
	}
	public QueueContrast(String name ,int age) {
		this.name = name;
		this.age = age;
	}
	public  static  QueueContrast getQueueContrast(){
		int age=(int)(1+Math.random()*(100));
		int id=(int)(1+Math.random()*(1000));
		QueueContrast userModel = new QueueContrast("messageId"+id, age);
		return userModel;
	}
	@Override
	public void run() {
		System.out.println("The priority for"+priority+"Mission accomplished！");
	}
	@Override
	public int compareTo(Object arg) {
		QueueContrast task = (QueueContrast)arg;
		if(this.priority == task.priority){
			return 0;
		}
		return this.priority>task.priority?1:-1;
	}

	public static void blockingQueue(final BlockingQueue<QueueContrast> queue) throws InterruptedException{
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			int priority = random.nextInt(1000);
			System.out.println("Element priority："+priority);
			queue.put( new QueueContrast(priority) );
		}

		new Thread(new Runnable() {
			public void run() {
				while( !Thread.currentThread().isInterrupted() ){
					try {
						queue.take().run();//Fetch the elements from the queue
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "QueueContrast{" +
				"priority=" + priority +
				", name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
