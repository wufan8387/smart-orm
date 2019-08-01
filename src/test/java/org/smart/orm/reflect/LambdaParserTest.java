package org.smart.orm.reflect;

import org.junit.Test;
import org.smart.orm.Account;

import java.awt.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class LambdaParserTest {

    @Test
    public void getGet() {

        LambdaParser.getGetter(PropertyInfo::getColumnName);

    }

    @Test
    public void getSet() {
        PropertyGetter<Account> getter = t->t.getId();
        Class<?> cls = getter.getClass();
        Type superclass = cls.getGenericSuperclass();
        System.out.println(superclass);
        Type[] types = cls.getGenericInterfaces();
        System.out.println(types[0]);
        ParameterizedType parameterizedType = (ParameterizedType) types[0];
        System.out.println(parameterizedType.getActualTypeArguments()[0]);
    }
}
