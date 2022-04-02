package com.minenash.languagecheck.troblesome;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.stream.Stream;

import static com.minenash.languagecheck.troblesome.Fields.NATIVE;
import static com.minenash.languagecheck.troblesome.LanguageClassLoader.all;
import static net.gudenau.lib.unsafe.Unsafe.trustedLookup;

public class Methods {

    private static final MethodHandle getDeclaredMethods = Stream.of(Class.class.getDeclaredMethods())
            .filter(method -> all(method, NATIVE) && method.getReturnType() == Method[].class)
            .map(method -> {
                try {
                    return trustedLookup.unreflectSpecial(method, method.getDeclaringClass());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .map(method -> method.type().parameterCount() > 1 ? MethodHandles.insertArguments(method, 1, false) : method)
            .max(Comparator.comparing(method -> {
                try {
                    return ((Method[]) method.invoke()).length;
                } catch (Throwable e) {
                    e.printStackTrace();
                    return null;
                }
            }))
            .get();

    private static final CacheMap<Class<?>, Method[]> methods = new CacheMap<>(IdentityHashMap::new);

    public static Stream<Method> of(Class<?> type) {
        return Stream.of(methods.computeIfAbsent(type, Methods::direct));
    }
    public static Method[] direct(Class<?> type) {
        try {
            return (Method[]) getDeclaredMethods.invokeExact(type);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }



}
