package com.queues;

import java.util.logging.Logger;

public class UserModel implements Comparable<UserModel> {

    private final Logger logger = Logger.getLogger(String.valueOf(UserModel.class));

    private String name;
    private int age;

    public UserModel(String name, int age) {
        this.name = name;
        this.age = age;
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


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(UserModel o) {
        logger.info("work at compareTo(Person o)");
        return this.name.compareTo(o.name);
    }
}

