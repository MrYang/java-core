package com.zz.pattern.proxy;

public class Target implements Targetable {

    @Override
    public void targetMethod() {
        System.out.println("this is a target method...");
    }
}
