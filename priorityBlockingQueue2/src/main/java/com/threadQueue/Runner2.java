package com.threadQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * queue after utilization
 */
public class Runner2 {


	public static void main(String[] args) throws InterruptedException {
		System.out.println("******LockFreePriorityQueue******");
		QueueContrast2.blockingQueue(new LockFreePriorityQueue<QueueContrast2>());
		TimeUnit.MILLISECONDS.sleep(1000);


		System.out.println("******PipelinedPriorityQueue******");
		List<QueueContrastByPipe> list2=new ArrayList<>();
		list2.add(new QueueContrastByPipe(10));
		list2.add(new QueueContrastByPipe(50));
		list2.add(new QueueContrastByPipe(100));
		list2.add(new QueueContrastByPipe(200));
		list2.add(new QueueContrastByPipe(500));
		list2.add(new QueueContrastByPipe(1000));
		list2.add(new QueueContrastByPipe(2000));
		list2.add(new QueueContrastByPipe(5000));
		list2.add(new QueueContrastByPipe(8000));
		list2.add(new QueueContrastByPipe(100000));
		list2.add(new QueueContrastByPipe(20000));
		list2.add(new QueueContrastByPipe(50000));
		for(QueueContrastByPipe queueContrast:list2){
			PipelinedPriorityQueue queue=new PipelinedPriorityQueue(list2.size(),queueContrast);
			QueueContrastByPipe.blockingQueue(queue);
		}
	}

}
