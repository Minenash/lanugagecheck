package com.minenash.languagecheck.troblesome;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.stream.Stream;

import static com.minenash.languagecheck.troblesome.LanguageClassLoader.all;
import static net.gudenau.lib.unsafe.Unsafe.trustedLookup;

public class Fields {
    public static final int NATIVE = 1 << 8;
    private static final MethodHandle getDeclaredFields = Methods.of(Class.class)
            .filter(method -> all(method, NATIVE) && method.getReturnType() == Field[].class)
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
                    return ((Field[]) method.invoke(Fields.class)).length;
                } catch (Throwable e) {
                    e.printStackTrace();
                    return null;
                }
            }))
            .get();

    private static final CacheMap<Class<?>, Field[]> fields = new CacheMap<>(IdentityHashMap::new);

    public static Field[] direct(Class<?> type) {
        try {
            return (Field[]) getDeclaredFields.invokeExact(type);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Stream<Field> of(Class<?> type) {
        return Stream.of(fields.computeIfAbsent(type, Fields::direct));
    }

}