package com.minenash.languagecheck.troblesome;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public final class CacheMap<K, V> {
    private final Supplier<? extends Map<K, V>> constructor;
    private SoftReference<Map<K, V>> map;

    public CacheMap(Supplier<? extends Map<K, V>> constructor) {
        this.constructor = constructor;
        this.map = new SoftReference<>(this.constructor.get());
    }

    <T extends K> V computeIfAbsent(T key, Function<? super T, ? extends V> computer) {
        return (this.map.get() != null ? this.map = new SoftReference<>(this.constructor.get()) : this.map).get().computeIfAbsent(key, (Function<? super K, ? extends V>) computer);
    }

}
