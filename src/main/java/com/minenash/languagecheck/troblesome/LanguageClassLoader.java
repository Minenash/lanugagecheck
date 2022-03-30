package com.minenash.languagecheck.troblesome;

import net.gudenau.lib.unsafe.Unsafe;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

import static net.gudenau.lib.unsafe.Unsafe.trustedLookup;

public class LanguageClassLoader {

    private static final ClassLoader loader = ClassLoader.getSystemClassLoader();
    private static final Class<?> URLClassPath = tryLoad("jdk.internal.loader.URLClassPath", "sun.misc.URLClassPath");
    private static final MethodHandle addURL = findVirtual(URLClassPath, "addURL", void.class, URL.class);
    private static final Object classPath = Unsafe.getReference(loader, Unsafe.objectFieldOffset(classPathField(loader.getClass())));



    public static void load(Path path) {
        try {
            addURL.invoke(classPath, path.toUri().toURL());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static Class<?> tryLoad(String... classes) {
        return Stream.of(classes).map(e -> {
            try {
                return loader.loadClass(e);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                return null;
            }
        }).filter(Objects::nonNull).findFirst().orElse(null);
    }

    private static Field classPathField(Class<?> loaderClass) {
        return classes(loaderClass)
                .flatMap(Fields::of)
                .filter(LanguageClassLoader::isInstance)
                .filter(field -> URLClassPath.isAssignableFrom(field.getType()))
                .findAny()
                .orElse(null);
    }
    private static <T> Stream<Class<?>> classes(Class<T> begin) {
        return Stream.iterate(begin, type -> type != Object.class, Class::getSuperclass);
    }

    private static MethodHandle findVirtual(Class<?> type, String name, Class<?> returnType, Class<?>... parameterTypes) {
        try {
            return trustedLookup.findVirtual(type, name, MethodType.methodType(returnType, parameterTypes));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final int STATIC       = 1 << 3;
    public static boolean isInstance(Member member) {
        return !all(member, STATIC);
    }

    public static boolean all(Member member, long target) {
        int m =member == null ? 0 : member.getModifiers();
        return (m & target) == target;
    }


}
