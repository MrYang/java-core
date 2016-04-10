package com.zz.pattern.proxy;

public class Proxy implements Targetable {
    private Target target;

    public Proxy() {
        this.target = new Target();
    }

    private void beforeMethod() {
        System.out.println("this is a method before proxy...");
    }

    private void afterMethod() {
        System.out.println("this is a method after proxy...");
    }

    /**
     * 在执行目标方法前后加了逻辑
     */
    @Override
    public void targetMethod() {
        beforeMethod();
        target.targetMethod();
        afterMethod();
    }
}
