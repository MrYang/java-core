package com.zz.multithread.chefeater;

public class ChefEaterTest {

    public static void main(String[] args) {
        Table t = new Table(10);
        new Chef("Chef1", t).start();
        new Chef("Chef2", t).start();
        new Chef("Chef3", t).start();
        new Chef("Chef4", t).start();
        new Eater("Eater1", t).start();
        new Eater("Eater2", t).start();
        new Eater("Eater3", t).start();
        new Eater("Eater4", t).start();
        new Eater("Eater5", t).start();
        new Eater("Eater6", t).start();
    }
}
