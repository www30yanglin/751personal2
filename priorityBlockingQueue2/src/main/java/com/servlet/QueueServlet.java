package com.servlet;

import com.threadQueue.LockFreePriorityQueue;
import com.threadQueue.QueueContrast;
import com.threadQueue.User;
import com.threadQueue.UserModel;

import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("ALL")
public class QueueServlet extends HttpServlet {

	PriorityBlockingQueue<UserModel>   persons = new PriorityBlockingQueue<>();;
	LinkedBlockingQueue<QueueContrast> persons2 = new LinkedBlockingQueue<QueueContrast>();
	LockFreePriorityQueue<QueueContrast> persons3 = new LockFreePriorityQueue<QueueContrast>();
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
//		int[] threads=new int[]{1,100,500,1000,2000,5000,10000};//number of threads
		int[] threads=new int[]{100,500,1000,2000,5000};//number of threads
		TreeMap<Integer,String> map=new TreeMap<Integer,String>();
		System.out.println("----------------------PriorityBlockingQueue start----------------------------");
		System.out.println("start executing loading data into PriorityBlockingQueue");
		for(int i=0;i<threads.length;i++){
			long start = System.currentTimeMillis();	//starting time
			for(int j=0;j<threads[i];j++){				//executed number of threads
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
			System.out.println("the number of threads is"+key+"time taken to load data into PriorityBlockingQueue is："+value);
		}
		System.out.println("----------------------PriorityBlockingQueue end----------------------------");
		System.out.println("----------------------BlockingQueue start----------------------------");
		TreeMap<Integer,String> map2=new TreeMap<Integer,String>();
		System.out.println("start executing loading data into LinkedBlockingQueue");
		for(int i=0;i<threads.length;i++){
			long start = System.currentTimeMillis();	//starting time
			for(int j=0;j<threads[i];j++){				//executed number of threads
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
			System.out.println("the number of threads is\"+key+\"time taken to load data into LinkedBlockingQueue is："+value);
		}

		System.out.println("----------------------LockFreePriorityQueue start----------------------------");
		TreeMap<Integer,String> map3=new TreeMap<Integer,String>();
		System.out.println("start executing loading data into LockFreePriorityQueue");
		for(int i=0;i<threads.length;i++){
			long start = System.currentTimeMillis();	//starting time
			for(int j=0;j<threads[i];j++){				//executed number of threads
				User user = new User();
				user.setQueueType("LockFreePriorityQueue");
				user.setPersons3(persons3);
				new Thread(user).start();
			}
			long end = System.currentTimeMillis();	//ending time
			long res= end-start; 					//result(time consumed)
			map3.put(threads[i],res+"");
		}
		key = null;
		value = null;
		iter = map3.keySet().iterator();
		while (iter.hasNext()) {
			key = (Integer)iter.next();
			value = (String)map3.get(key);
			System.out.println("number of threads is"+key+"time taken to load data into queue is："+value);
		}
		System.out.println("----------------------LockFreePriorityQueue end----------------------------");


		//Initialize page data
		String opData="";
		for(int i=0;i<threads.length;i++){
			opData=opData+"'"+"Threads："+threads[i]+"',";
		}
		opData=opData.substring(0,opData.length()-1);
		System.out.println("opData="+opData);
		session.setAttribute("opData", opData);	//options

		String datavn="";
		for (Integer k : map.keySet()) {
			datavn=datavn+"{ value: "+map.get(k)+", name: '"+k+"' },";
		}
		datavn=datavn.substring(0,datavn.length()-1);
		session.setAttribute("map1", datavn);
		datavn="";
		for (Integer k : map2.keySet()) {
			datavn=datavn+"{ value: "+map2.get(k)+", name: '"+k+"' },";
		}
		datavn=datavn.substring(0,datavn.length()-1);
		session.setAttribute("map2", datavn);
		datavn="";
		for (Integer k : map3.keySet()) {
			datavn=datavn+"{ value: "+map3.get(k)+", name: '"+k+"' },";
		}
		datavn=datavn.substring(0,datavn.length()-1);
		session.setAttribute("map3", datavn);
		request.getRequestDispatcher("/WEB-INF/pages/reg.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
