package com.zz.multithread.chefeater;

import java.util.LinkedList;

public class Table extends LinkedList {

    private int maxSize;

    public Table(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void putFood(Food f) {
        while (this.size() >= this.maxSize) {
            try {
                this.wait();
            } catch (Exception e) {
            }
        }
        this.add(f);
        notifyAll();
    }

    public synchronized Food getFood() {
        while (this.size() <= 0) {
            try {
                this.wait();
            } catch (Exception e) {
            }
        }
        Food f = (Food) this.removeFirst();
        notifyAll();
        return f;
    }
}
