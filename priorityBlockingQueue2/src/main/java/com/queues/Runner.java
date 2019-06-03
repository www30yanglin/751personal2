package com.queues;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * entry
 */
public class Runner {

    private final static Logger logger = Logger.getLogger(String.valueOf(Runner.class));
    public static void main(String[] args) {
		int[] threads=new int[]{1,100,500,1000,2000,5000,10000};//number of thread
		TreeMap<Integer,String> map=new TreeMap<Integer,String>();
		logger.info("start putting elements in queue");
        for(int i=0;i<threads.length;i++){
			long start = System.currentTimeMillis();	//starting time
			for(int j=0;j<threads[i];j++){			//number of excuted thread
				User user = new User();
				new Thread(user).start();
			}
			long end = System.currentTimeMillis();	//ending time
			long res= end-start; //benchmark result
			map.put(threads[i],res+"");
		}
		Integer key = null;
		String value = null;
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			key = (Integer)iter.next();
			value = (String)map.get(key);
			System.out.println("the number of thread is "+key+"time taken to store elements in queue:"+value);
		}
    }
}
