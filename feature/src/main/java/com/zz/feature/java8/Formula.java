package com.zz.feature.java8;

public interface Formula {
    double calculate(int a);

    default double sqrt(int a) {
        return Math.sqrt(a);
    }

    static double log(int a) {
        return Math.log(a);
    }
}
