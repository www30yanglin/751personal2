package com.threadQueue;

import java.util.Comparator;
import java.util.Random;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

class QueueContrastByPipe implements Runnable,Comparator ,Comparable{
	private int priority;

	public QueueContrastByPipe(int priority ) {
		this.priority = priority;
	}
	@Override
	public void run() {
		System.out.println("The priority for"+priority+"Mission accomplished！");
	}
	public int compareTo(Object arg) {
		QueueContrastByPipe task = (QueueContrastByPipe)arg;
		if(this.priority == task.priority){
			return 0;
		}
		return this.priority>task.priority?1:-1;
	}

	public static void blockingQueue(PipelinedPriorityQueue queue) {
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			int priority = random.nextInt(100);
			System.out.println("Element priority："+priority);
			queue.add( new QueueContrastByPipe(priority) );
		}

		new Thread(new Runnable() {
			public void run() {
				while( !Thread.currentThread().isInterrupted() ){
					if(queue.isEmpty()){
						queue.peek();
						queue.poll();
					}
				}
			}
		}).start();
	}

	@Override
	public int compare(Object o1, Object o2) {
		QueueContrastByPipe olTask = (QueueContrastByPipe)o1;
		QueueContrastByPipe o2Task = (QueueContrastByPipe)o1;
		if(olTask.priority == o2Task.priority){
			return 0;
		}
		return olTask.priority>o2Task.priority?1:-1;
	}

	@Override
	public Comparator reversed() {
		return null;
	}

	@Override
	public Comparator thenComparing(Comparator other) {
		return null;
	}

	@Override
	public Comparator thenComparingInt(ToIntFunction keyExtractor) {
		return null;
	}

	@Override
	public Comparator thenComparingLong(ToLongFunction keyExtractor) {
		return null;
	}

	@Override
	public Comparator thenComparingDouble(ToDoubleFunction keyExtractor) {
		return null;
	}

	@Override
	public Comparator thenComparing(Function keyExtractor) {
		return null;
	}

	@Override
	public Comparator thenComparing(Function keyExtractor, Comparator keyComparator) {
		return null;
	}
}
