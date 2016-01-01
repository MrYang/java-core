package com.zz.feature.java8;

public class FunctionalInterfaceImpl {

    public void implOne() {
        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        Integer target = converter.convert("123");
        System.out.println(target);
    }

    public void implTwo() {
        Converter<String, Integer> converter = Integer::valueOf;
        Integer target = converter.convert("123");
        System.out.println(target);
    }

    public void implThree() {
        int num = 1; // num 默认为final��
        Converter<Integer, String> stringConverter = (from) -> String.valueOf(from + num);
        stringConverter.convert(2);     // 3
    }

    private void doubleQuote() {
        Something something = new Something();
        Converter<String, String> converter = something::startsWith;
        String converted = converter.convert("Java");
        System.out.println(converted);
    }

    class Something {
        String startsWith(String s) {
            return String.valueOf(s.charAt(0));
        }
    }
}
