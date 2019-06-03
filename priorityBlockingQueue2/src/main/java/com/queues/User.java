package com.queues;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * create information entering queue
 */
@SuppressWarnings("ALL")
public class User implements Runnable {
	private String name;
	private int age;
	PriorityBlockingQueue<UserModel> persons;
	public User() {
	}

	public PriorityBlockingQueue<UserModel> getPersons() {
		return persons;
	}

	public void setPersons(PriorityBlockingQueue<UserModel> persons) {
		this.persons = persons;
	}

	private PriorityBlockingQueue<UserModel> putQueuePersons(UserModel person){
        persons = new PriorityBlockingQueue<>();
            persons.put(person);
            return persons;
    }

    private UserModel getUser(){
		int age=(int)(1+Math.random()*(100));
		int id=(int)(1+Math.random()*(1000));
		UserModel userModel = new UserModel("messageId"+id, age);
        return userModel;
    }

    @Override
    public void run() {
		putQueuePersons(getUser());
		//System.out.println(putQueuePersons(getUser()));

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

}
