package com.zz.pattern.factory.abstractfactory;

public class WesternTrousers extends Trousers {

    private int waistSize;
    private int height;
    private String name;

    public WesternTrousers(String name, int waistSize, int height) {
        this.name = name;
        this.waistSize = waistSize;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public int getWaistSize() {
        return waistSize;
    }
}