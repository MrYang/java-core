package com.zz.feature.java8;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Lambda {

    public void simpleLambda() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });

        Collections.sort(names, (String a, String b) -> {
            return b.compareTo(a);
        });

        Collections.sort(names, (String a, String b) -> b.compareTo(a));

        Collections.sort(names, (a, b) -> b.compareTo(a));

        names.forEach((str) -> System.out.println(str));

        names.forEach(System.out::println);
    }

    public void simpleFilter() {
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        System.out.println("Languages which starts with J :");
        filter(languages, (str) -> str.startsWith("J"));

        System.out.println("Languages which ends with a ");
        filter(languages, (str) -> str.endsWith("a"));

        System.out.println("Print all languages :");
        filter(languages, (str) -> true);

        System.out.println("Print no language : ");
        filter(languages, (str) -> false);

        System.out.println("Print language whose length greater than 4:");
        filter(languages, (str) -> str.length() > 4);
    }

    public void complexFilter() {
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
        Predicate<String> startWithJ = (str) -> str.startsWith("J");
        Predicate<String> strLength = (str) -> (str.length() == 4);
        languages.stream().filter(startWithJ.and(strLength)).forEach((str) -> System.out.println("both startWithJ and strLength is :" + str));
    }

    public void filter(List<String> names, Predicate<String> condition) {
        names.forEach((name) -> {
            if (condition.test(name)) {
                System.out.println(name + " ");
            }
        });

        names.stream().sorted().filter(name -> condition.test(name)).forEach(name -> System.out.println(name + " "));
        names.stream().sorted().filter(condition::test).forEach(name -> System.out.println(name + " "));
    }

    public void list() {
        List<Integer> lists = Arrays.asList(10, 20, 30, 40);
        lists.stream().map((xInt) -> xInt + 8).forEach((xInt) -> System.out.println("after lambda is :" + xInt));

        List<String> countries = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.", "Canada");
        String result = countries.stream().map((str) -> str.toUpperCase()).collect(Collectors.joining(","));
        String result2 = countries.stream().map(String::toUpperCase).collect(Collectors.joining(","));
        System.out.println(result);
    }

    public void map() {
        Map<Integer, String> map = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }

        map.forEach((id, val) -> System.out.println(val));

        map.computeIfPresent(3, (num, val) -> val + num);
        map.get(3);             // val33

        map.computeIfPresent(9, (num, val) -> null);
        map.containsKey(9);     // false

        map.computeIfAbsent(23, num -> "val" + num);
        map.containsKey(23);    // true

        map.computeIfAbsent(3, num -> "bam");
        map.get(3);             // val33

        map.remove(3, "val3");
        map.get(3);             // val33

        map.remove(3, "val33");
        map.get(3);             // null

        map.getOrDefault(42, "not found");  // not found

        map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
        map.merge(9, "val9", String::concat);
        map.get(9);             // val9

        map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
        map.get(9);             // val9concat
    }

    public void builtInLambda() {
        Predicate<String> predicate = (s) -> s.length() > 0;

        predicate.test("foo");              // true
        predicate.negate().test("foo");     // false

        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;

        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();

        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);

        backToString.apply("123");     // "123"
    }
}
