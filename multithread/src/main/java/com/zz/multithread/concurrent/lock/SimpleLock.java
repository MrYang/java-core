package com.zz.multithread.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author Mr.Yang
 * @since 2017-07-14
 */
public class SimpleLock {

    private static class Sync extends AbstractQueuedSynchronizer {

        public Sync() {
            super();
        }

        @Override
        protected boolean tryAcquire(int arg) {
            return compareAndSetState(0, 1);
        }

        @Override
        protected boolean tryRelease(int arg) {
            setState(0);
            return true;
        }
    }

    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

    private static class LockThread extends Thread {

        private String name;
        private SimpleLock simpleLock;


        private LockThread(String name, SimpleLock simpleLock) {
            this.name = name;
            this.simpleLock = simpleLock;
        }

        @Override
        public void run() {
            try {
                simpleLock.lock();

                System.out.println(name + " get lock");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                simpleLock.unlock();
                System.out.println(name + " release the lock");
            }
        }
    }

    public static void main(String[] args) {
        final SimpleLock mutex = new SimpleLock();
        for (int i = 0; i < 10; i++) {
            LockThread t = new LockThread("t" + i, mutex);
            t.start();
        }
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main thread exit!");
    }
}
