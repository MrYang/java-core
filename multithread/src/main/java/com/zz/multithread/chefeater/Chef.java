package com.zz.multithread.chefeater;


import java.util.Random;

public class Chef extends Thread {

    Table t;
    String name;
    Random r = new Random(12345);

    public Chef(String name, Table t) {
        this.t = t;
        this.name = name;
    }

    public void run() {
        while (true) {
            Food f = make();
            System.out.println(name + " put a Food:" + f);
            t.putFood(f);
        }
    }

    private Food make() {
        try {
            Thread.sleep(200 + r.nextInt(200));
        } catch (Exception e) {
        }
        return new Food();
    }
}
