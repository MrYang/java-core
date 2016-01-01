package com.zz.multithread.basic;


public class RunableThreadDiff {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            Thread t = new MyThread();
            t.start();
        }
        Thread.sleep(1000);//让上面的线程运行完成

        R r = new R();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(r);
            t.start();
        }
    }

    static class MyThread extends Thread {
        public int x = 0;

        public void run() {
            System.out.println(++x);
        }
    }

    static class R implements Runnable {
        private int x = 0;

        public void run() {
            System.out.println(++x);
        }
    }

}
