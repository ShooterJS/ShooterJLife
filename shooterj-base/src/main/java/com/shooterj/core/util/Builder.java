package com.shooterj.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 通用Builder
 */
public class Builder<T> {

    private final Supplier<T> instantiator;
    private List<Consumer<T>> instantiatorModifiers = new ArrayList<>();
    private List<Consumer<T>> keyValueModifiers = new ArrayList<>();

    public Builder(Supplier<T> instantiator) {
        this.instantiator = instantiator;
    }

    public static <T> Builder<T> of(Supplier<T> instantiator) {
        return new Builder<>(instantiator);
    }

    public <U> Builder<T> with(BiConsumer<T, U> consumer, U value) {
        Consumer<T> c = instance -> consumer.accept(instance, value);
        instantiatorModifiers.add(c);
        return this;
    }

    public <K, V> Builder<T> with(KeyValueConsumer<T, K, V> consumer, K key, V value) {
        Consumer<T> c = instance -> consumer.accept(instance, key, value);
        keyValueModifiers.add(c);
        return this;
    }

    public T build() {
        T value = instantiator.get();
        instantiatorModifiers.forEach(modifier -> modifier.accept(value));
        keyValueModifiers.forEach(keyValueModifier -> keyValueModifier.accept(value));
        instantiatorModifiers.clear();
        keyValueModifiers.clear();
        return value;
    }

    @FunctionalInterface
    public interface KeyValueConsumer<T, K, V> {

        void accept(T t, K k, V v);

        default KeyValueConsumer<T, K, V> andThen(KeyValueConsumer<? super T, ? super K, ? super V> after) {
            Objects.requireNonNull(after);

            return (t, k, v) -> {
                accept(t, k, v);
                after.accept(t, k, v);
            };
        }
    }

}
