package com.threadQueue;

import java.util.Random;

class QueueContrast2 implements Runnable,Comparable{
	private int priority;

	public QueueContrast2(int priority ) {
		this.priority = priority;
	}
	@Override
	public void run() {
		System.out.println("The priority for "+priority+" Mission completed！");
	}
	@Override
	public int compareTo(Object arg) {
		QueueContrast2 task = (QueueContrast2)arg;
		if(this.priority == task.priority){
			return 0;
		}
		return this.priority>task.priority?1:-1;
	}

	public static void blockingQueue(final LockFreePriorityQueue<QueueContrast2> queue) {
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			int priority = random.nextInt(100);
			System.out.println("Element priority："+priority);
			queue.put( new QueueContrast2(priority) );
		}
		new Thread(new Runnable() {
			public void run() {
				while( !Thread.currentThread().isInterrupted() ){
					if(queue.isEmpty()){	//Object to determine
						queue.peek().run(); //Fetch the elements from the queue
						queue.poll();
					}
				}
			}
		}).start();
	}
}
