package com.queues4;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.queues4.QueueContrast.blockingQueue;

/**
 * Normal blocking versus priority teams
 */
public class Runner {


	public static void main(String[] args) throws InterruptedException {
		System.out.println("******Start testing the normal blocking queue******");
		blockingQueue(new LinkedBlockingQueue<QueueContrast>());
		TimeUnit.MILLISECONDS.sleep(1000);
		System.out.println("******Start testing the priority queue******");
		blockingQueue(new PriorityBlockingQueue<QueueContrast>());
	}
}
