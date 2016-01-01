package com.zz.multithread.chefeater;

import java.util.Random;

public class Eater extends Thread {
    Table t;
    String name;
    Random r = new Random(54321);

    public Eater(String name, Table t) {
        this.t = t;
        this.name = name;
    }

    public void run() {
        while (true) {
            Food f = t.getFood();
            System.out.println(name + " get a Food:" + f);
            eat(f);
        }
    }

    private void eat(Food f) {

        try {
            Thread.sleep(400 + r.nextInt(200));
        } catch (Exception e) {
        }
    }
}