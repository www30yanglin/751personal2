package com.queues4;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 *
 */
class QueueContrast implements Runnable,Comparable{
	private int priority;

	public QueueContrast(int priority ) {
		this.priority = priority;
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
}
