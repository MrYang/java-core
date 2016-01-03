package com.zz.rpc.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyHandler implements InvocationHandler {

    private Object target;

    public Object newProxyInstance(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before " + method.getName());
        Object ret;
        try {
            //调用目标方法
            ret = method.invoke(target, args);
            System.out.println("after " + method.getName());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error " + method.getName());
            throw e;
        }
        return ret;
    }
}
