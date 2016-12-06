package com.zz.reflection;

import com.zz.common.entity.User;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * getFields() getMethods() getConstructors()（还有带字符串参数，给定名称的形式）
 * 分别返回类支持的public域、方法和构造器数组，其中包括超类的公有成员
 * <p>
 * getDeclaredFields(),getDeclaredMethods(),getDeclaredConstructors()（还有给定名称的形式）
 * 分别返回类中声明的全部域、方法和构造器数组。其中包括私有和保护成员，但不包括超类的成员
 * <p>
 * Type是Java编程语言中所有类型的普通的父接口，需要注意Type 的类层次结构
 * Method, Parameter, Field, Annotation 与Class 类似, 具体用法可以参见api
 */
public class ReflectShow {

    public void show() throws Exception {
        Class clazz = Class.forName("com.zz.common.entity.User");
        User user = (User) clazz.newInstance();

        Constructor constructor = clazz.getConstructor(new Class[]{String.class, Date.class});
        System.out.println(constructor.getName());

        for (Type type : clazz.getGenericInterfaces()) {
            System.out.println(type.getTypeName());
        }
        System.out.println(clazz.getGenericSuperclass().getTypeName());
        for (Class type : clazz.getInterfaces()) {
            System.out.println(type.getTypeName());
        }

        // 类及从超类和超接口继承的公共方法
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }

        // 包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法。
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod.getName());
        }

        Method setUsernameMethod = clazz.getMethod("setUsername", new Class[]{String.class});
        setUsernameMethod.invoke(user, "yxb");
        System.out.println(user.getUsername());
    }

    public void array() throws Exception {
        Class<?> classType = Class.forName("java.lang.String");
        Object array = Array.newInstance(classType, 10);
        Array.set(array, 5, "hello");
        String s = (String) Array.get(array, 5);
        System.out.println(s);
    }

    public List<Class<?>> scanPackage(String packageName, List<Class<?>> classes) throws Exception {
        String pack = packageName.replace('.', '/');
        URL url = Thread.currentThread().getContextClassLoader().getResource(pack);
        if (url == null) {
            throw new RuntimeException("包不存在");
        }

        File dir = new File(url.toURI());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanPackage(packageName + "." + file.getName(), classes);
            } else {
                String className = packageName + "." + file.getName();
                System.out.println(className);
                String clazz = className.substring(0, className.length() - 6);
                classes.add(Class.forName(clazz));
            }
        }

        return classes;
    }

    public static void main(String[] args) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        new ReflectShow().scanPackage("com.zz.reflection", classes);
        System.out.println(classes.size());
    }
}
