package com.zz.pattern.adapter;

/**
 * 适配器对象实现原有接口
 * 适配器对象组合一个实现新接口的对象（这个对象也可以不实现一个接口，只是一个单纯的对象）
 * 对适配器原有接口方法的调用被委托给新接口的实例的特定方法
 */

public class SocketAdapter implements DBSocketInterface {

    private GBSocketInterface gbSocket;

    public SocketAdapter(GBSocketInterface gbSocket) {
        this.gbSocket = gbSocket;
    }

    @Override
    public void powerWithTwoRound() {

        gbSocket.powerWithThreeFlat();
    }
}
