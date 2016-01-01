package com.zz.feature.java8;

@FunctionalInterface
public interface Converter<F, T> {
    T convert(F from);
}
