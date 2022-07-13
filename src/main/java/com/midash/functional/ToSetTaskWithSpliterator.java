package com.midash.functional;

import java.util.function.Function;
import java.util.HashSet;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.Callable;

class ToSetTaskWithSpliterator <T, R> implements Callable<Set<R>> {
    ToSetConsumer<T, R> consumer;
    Spliterator<T> spliterator;

    ToSetTaskWithSpliterator(Spliterator<T> spliterator, Function<T, R> tranformer) {
        this.consumer = new ToSetConsumer<>(new HashSet<R>(), tranformer);
        this.spliterator = spliterator;
    }

    
    @Override
    public Set<R> call() throws Exception {
        spliterator.forEachRemaining(consumer);
        return consumer.result;
    }
    
}
