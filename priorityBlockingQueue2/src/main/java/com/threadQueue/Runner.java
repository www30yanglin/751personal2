package com.threadQueue;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Logger;

/**
 * 队列总入口
 */
@SuppressWarnings("ALL")
public class Runner {
	 public static PriorityBlockingQueue<UserModel> persons = new PriorityBlockingQueue<>();;
	public static LinkedBlockingQueue<QueueContrast> persons2 = new LinkedBlockingQueue<QueueContrast>();
	public static LockFreePriorityQueue<QueueContrast> persons3 = new LockFreePriorityQueue<QueueContrast>();
    private final static Logger logger = Logger.getLogger(String.valueOf(Runner.class));
    public static void main(String[] args) {

		int[] threads=new int[]{100,500,1000,2000,5000};//number of threads
//		int[] threads=new int[]{1,10,20,50};//number of threads
		TreeMap<Integer,String> map=new TreeMap<Integer,String>();
		System.out.println("----------------------PriorityBlockingQueue start----------------------------");
		System.out.println("start excuting storing data in PriorityBlockingQueue");
		for(int i=0;i<threads.length;i++){
			long start = System.currentTimeMillis();	//starting time
			for(int j=0;j<threads[i];j++){				//excuted number of threads
				User user = new User();
				user.setQueueType("PriorityBlockingQueue");
				user.setPersons(persons);
				Thread thread=new Thread(user);
				thread.start();
			}
			long end = System.currentTimeMillis();	//ending time
			long res= end-start;
			map.put(threads[i],res+"");
		}
		Integer key = null;
		String value = null;
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			key = (Integer)iter.next();
			value = (String)map.get(key);
			System.out.println("number of threads is"+key+"time taken to store data in PriorityBlockingQueue is："+value);
		}
		System.out.println("----------------------PriorityBlockingQueue end----------------------------");
		System.out.println("----------------------LinkedBlockingQueue start----------------------------");
		TreeMap<Integer,String> map2=new TreeMap<Integer,String>();
		System.out.println("start excuting storing data in LinkedBlockingQueue ");
		for(int i=0;i<threads.length;i++){
			long start = System.currentTimeMillis();	//starting time
			for(int j=0;j<threads[i];j++){				//excuted number of threads
				User user = new User();
				user.setQueueType("LinkedBlockingQueue");
				user.setPersons2(persons2);
				new Thread(user).start();
			}
			long end = System.currentTimeMillis();
			long res= end-start;
			map2.put(threads[i],res+"");
		}
		key = null;
		value = null;
		iter = map2.keySet().iterator();
		while (iter.hasNext()) {
			key = (Integer)iter.next();
			value = (String)map2.get(key);
			System.out.println("number of threads is"+key+"time taken to store data in LinkedBlockingQueue is："+value);
		}

		System.out.println("----------------------LockFreePriorityQueue start----------------------------");
		TreeMap<Integer,String> map3=new TreeMap<Integer,String>();
		System.out.println("start excuting storing data in LockFreePriorityQueue");
		for(int i=0;i<threads.length;i++){
			long start = System.currentTimeMillis();	//starting time
			for(int j=0;j<threads[i];j++){				//excuted number of threads
				User user = new User();
				user.setQueueType("LockFreePriorityQueue");
				user.setPersons3(persons3);
				new Thread(user).start();
			}
			long end = System.currentTimeMillis();	//ending time
			long res= end-start; 					//result
			map3.put(threads[i],res+"");
		}
		key = null;
		value = null;
		iter = map3.keySet().iterator();
		while (iter.hasNext()) {
			key = (Integer)iter.next();
			value = (String)map3.get(key);
			System.out.println("number of threads is"+key+"time taken to store data in LockFreePriorityQueue is："+value);
		}
		System.out.println("----------------------LockFreePriorityQueue end----------------------------");
	}
}
