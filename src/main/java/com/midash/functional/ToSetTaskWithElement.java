package com.midash.functional;

import java.util.Set;
import java.util.concurrent.Callable;

import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;

@Slf4j
class ToSetTaskWithElement <T, R> implements Callable<Set<R>> {
    ToSetConsumer<T, R> consumer;
    T element;

    ToSetTaskWithElement(Function<T, R> tranformer) {
        this.consumer = new ToSetConsumer<>(new HashSet<R>(), tranformer);        
    }

    
    public T getElement() {
        return element;
    }


    public void setElement(T element) {
        this.element = element;
    }


    @Override
    public Set<R> call() throws Exception {
        consumer.result.add(this.consumer.transformer.apply(element));
        return consumer.result;
    }
    
}
