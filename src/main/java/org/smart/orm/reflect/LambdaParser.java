package org.smart.orm.reflect;

import org.smart.orm.SmartORMException;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LambdaParser {

    private final static String IS = "is";

    private final static String GET = "get";

    private static Map<Class<?>, SerializedLambda> lambdaMap = new HashMap<>();

    public static <T> Field getGet(Getter<T> fn) {
        SerializedLambda lambda = serialize(fn);
        String methodName = lambda.getImplMethodName();
        String clsName = lambda.getImplClass();
        String prefix = null;
        if (methodName.startsWith(GET)) {
            prefix = GET;
        } else if (methodName.startsWith(IS)) {
            prefix = IS;
        }
        if (prefix == null) {
            throw new SmartORMException(String.format("无效的getter方法: %s ", methodName));
        }

        String fieldName = methodName.substring(0, prefix.length());

        fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
        try {
            Class<?> cls = Class.forName(clsName);
            return cls.getDeclaredField(fieldName);
        } catch (Exception e) {
            throw new SmartORMException(e);
        }

    }

    private static SerializedLambda serialize(Serializable fn) {

        SerializedLambda lambda = lambdaMap.get(fn.getClass());
        if (lambda == null) {
            try {

                Method method = fn.getClass().getDeclaredMethod("writeReplace");
                method.setAccessible(Boolean.TRUE);
                lambda = (SerializedLambda) method.invoke(fn);
                lambdaMap.put(fn.getClass(), lambda);
            } catch (Exception e) {
                throw new SmartORMException(e);
            }
        }

        return lambda;
    }

}
