package com.threadQueue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 创建信息塞入队列
 */
@SuppressWarnings("ALL")
public class User implements Runnable {
	private String name;
	private int age;
	private String  queueType;
	PriorityBlockingQueue<UserModel> persons;
	LinkedBlockingQueue<QueueContrast> persons2;
	LockFreePriorityQueue<QueueContrast> persons3;

	public LockFreePriorityQueue<QueueContrast> getPersons3() {
		return persons3;
	}

	public void setPersons3(LockFreePriorityQueue<QueueContrast> persons3) {
		this.persons3 = persons3;
	}

	public User() {
	}

	public PriorityBlockingQueue<UserModel> getPersons() {
		return persons;
	}

	public void setPersons(PriorityBlockingQueue<UserModel> persons) {
		this.persons = persons;
	}

	private PriorityBlockingQueue<UserModel> putQueuePersons(UserModel person)  {
        //persons = new PriorityBlockingQueue<>();
            persons.put(person);
		try {
			UserModel e=persons.take();
			System.out.println("PriorityBlockingQueue.take"+e);;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return persons;
    }

	private LinkedBlockingQueue<QueueContrast> putLinkedBlockingQueue(QueueContrast person)  {
//		persons2 = new LinkedBlockingQueue<QueueContrast>();
		persons2.add(person);
		try {
			persons2.take();
			System.out.println("LinkedBlockingQueue.take"+persons2.take());;

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return persons2;
	}
	private LockFreePriorityQueue<QueueContrast> putLockFreePriorityQueue(QueueContrast person){
//		persons3 = new LockFreePriorityQueue<QueueContrast>();
		persons3.put(person);
		System.out.println("LockFreePriorityQueue.peek"+persons3.peek());;
		return persons3;
	}

    private UserModel getUser(){
		int age=(int)(1+Math.random()*(100));
		int id=(int)(1+Math.random()*(1000));
		UserModel userModel = new UserModel("messageId"+id, age);
        return userModel;
    }

    @Override
    public void run() {
		if(queueType.equalsIgnoreCase("PriorityBlockingQueue")){
			putQueuePersons(getUser());
		}else if(queueType.equalsIgnoreCase("LinkedBlockingQueue")){
			putLinkedBlockingQueue(QueueContrast.getQueueContrast());
		}else if(queueType.equalsIgnoreCase("LockFreePriorityQueue")){
			putLockFreePriorityQueue(QueueContrast.getQueueContrast());
		}
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

	public String getQueueType() {
		return queueType;
	}

	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}

	public LinkedBlockingQueue<QueueContrast> getPersons2() {
		return persons2;
	}

	public void setPersons2(LinkedBlockingQueue<QueueContrast> persons2) {
		this.persons2 = persons2;
	}
}
