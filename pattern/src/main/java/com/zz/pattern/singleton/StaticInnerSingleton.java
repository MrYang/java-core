package com.zz.pattern.singleton;

public class StaticInnerSingleton {

    private static class SingletonHolder {
        private static final StaticInnerSingleton INSTANCE = new StaticInnerSingleton();
    }

    private StaticInnerSingleton() {
    }

    public static final StaticInnerSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
