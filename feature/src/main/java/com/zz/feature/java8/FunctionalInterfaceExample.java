package com.zz.feature.java8;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Mr.Yang
 * @since 2017-06-07
 */
public class FunctionalInterfaceExample {

    public void function() {
        // 接受T参数,返回R
        Function<Integer, String> function = x -> "int to string:" + x;
        System.out.println(function.apply(3));
    }

    public void supplier() {
        Supplier<String> supplier = () -> "return supplier";    // 无参数,返回T
        System.out.println(supplier.get());
    }

    public void consumer(){
        Consumer<String> consumer = System.out::println;    // 无参数,无返回值

        consumer.accept("abc");
    }

    public void predicate(){
        Predicate<String> predicate = x -> x.length() > 0;  // 接受T参数,返回布尔值

        System.out.println(predicate.test("string"));
    }
}
