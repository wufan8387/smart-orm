package org.smart.orm;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LambdaParser {

    private final static String IS = "is";

    private final static String GET = "get";

    private final static String SET = "set";

    private static Map<Class, SerializedLambda> CLASS_LAMDBA_MAP = new HashMap<>();

    public static <T> String getGet(Getter<T> fn) {
        SerializedLambda lambda = serialize(fn);
        String methodName = lambda.getImplMethodName();
        String prefix = null;
        if (methodName.startsWith(GET)) {
            prefix = GET;
        } else if (methodName.startsWith(IS)) {
            prefix = IS;
        }
        if (prefix == null) {
            throw new SmartORMException("无效的getter方法: " + methodName)
        }

        methodName = methodName.substring(0, prefix.length())

        methodName = Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);

        return methodName;
    }

    public static <T, R> String getSet(Setter<T, R> fn) {
        SerializedLambda lambda = serialize(fn);
        String methodName = lambda.getImplMethodName();
        if (!methodName.startsWith(SET)) {
            throw new SmartORMException("无效的setter方法: " + methodName);
        }

        methodName = methodName.substring(0, SET.length());

        methodName = Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);

        return methodName;
    }

    private static SerializedLambda serialize(Serializable fn) {

        SerializedLambda lambda = CLASS_LAMDBA_MAP.get(fn.getClass());
        if (lambda == null) {
            try {

                Method method = fn.getClass().getDeclaredMethod("writeReplace");
                method.setAccessible(Boolean.TRUE);
                lambda = (SerializedLambda) method.invoke(fn);
                CLASS_LAMDBA_MAP.put(fn.getClass(), lambda);
            } catch (Exception e) {
                throw new SmartORMException(e);
            }
        }
        return lambda;
    }

}







