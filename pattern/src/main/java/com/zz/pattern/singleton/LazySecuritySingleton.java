package com.zz.pattern.singleton;

public class LazySecuritySingleton {

    private static LazySecuritySingleton instance;

    private LazySecuritySingleton() {
    }

    public static synchronized LazySecuritySingleton getInstance() {
        if (instance == null) {
            instance = new LazySecuritySingleton();
        }
        return instance;
    }
}
