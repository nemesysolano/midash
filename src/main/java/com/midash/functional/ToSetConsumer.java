package com.midash.functional;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

class ToSetConsumer<T,R> implements Consumer<T> {
    Set<R> result;
    Function<T, R> transformer;

    ToSetConsumer(Set<R> result, Function<T,R> transformer) {
        this.result = result;
        this.transformer = transformer;
    }

    @Override
    public void accept(T t) {
        if(t != null) {
            result.add(transformer.apply(t));
        }
    }
    
}
