package com.zz.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectGenerics {

    public Type[] getParameterizedTypes(Class clazz) {
        Type superclassType = clazz.getGenericSuperclass();
        if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
            return null;
        }
        return ((ParameterizedType) superclassType).getActualTypeArguments();
    }
}
