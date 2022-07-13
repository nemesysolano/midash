package com.midash.functional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapReduce {

    public static <T, R> Set<R> toSet(ForkJoinPool executor, Collection<T> collection, Function<T, R> transformer) {
        log.debug("reducing collection");
        if (collection == null || collection.size() == 0) {
            return Set.of();
        }

        Spliterator<T> spliterator = collection.spliterator();
        int listSize = collection.size();
        int cores = Runtime.getRuntime().availableProcessors();
        int batchSize = listSize / cores;
        Spliterator<T> subListSpliterator;
        List<ToSetTaskWithSpliterator<T, R>> tasks = new LinkedList<>();
        
        while (spliterator.estimateSize() > batchSize && (subListSpliterator = spliterator.trySplit()) != null) {
            tasks.add(new ToSetTaskWithSpliterator<T, R>(subListSpliterator, transformer));
        }
        tasks.add(new ToSetTaskWithSpliterator<T, R>(spliterator, transformer));
        
        executor.invokeAll(tasks);
        HashSet<R> result = new HashSet<>();

        tasks.forEach(task -> result.addAll(task.consumer.result));
        return result;        
    }

    public  static <T, R> Set<R> toSet(Collection<T> collection, Function<T, R> transformer) {
       return toSet(ForkJoinPool.commonPool(), collection, transformer);
    }
  
    private static <T, R>  ArrayList<ToSetTaskWithElement<T, R>> createTaskList(Function<T, R> transformer) {
        int cores = Runtime.getRuntime().availableProcessors();    
        ArrayList<ToSetTaskWithElement<T, R>> tasks = new ArrayList<>(cores);        
        for(int index = 0; index < cores; index++) {
            tasks.add(new ToSetTaskWithElement<>(transformer));
        }

        return tasks;
    }

    public static <T, R> Set<R> toSet(ForkJoinPool executor, Iterable<T> iterable, Function<T, R> transformer) {
        log.debug("reducing from collection");
        if (iterable == null) {
            return Set.of();
        }

        Spliterator<T> spliterator = iterable.spliterator();
        int cores = Runtime.getRuntime().availableProcessors();    
        ArrayList<ToSetTaskWithElement<T, R>> tasks = createTaskList(transformer);
        final ElementWrapper<T> cutoff = new ElementWrapper<T>(0);

        while(spliterator.tryAdvance(element -> cutoff.element = element)) {            
            if(cutoff.value == cores) {
                executor.invokeAll(tasks);
                cutoff.value = 0;
            }

            tasks.get(cutoff.value).setElement(cutoff.element);
            cutoff.value ++;
        }

        if(cutoff.value > 0) {
           executor.invokeAll(tasks.subList(0, cutoff.value));
        }

        HashSet<R> result = new HashSet<>();

        tasks.forEach(task -> result.addAll(task.consumer.result));
        return result;        
    }

    public  static <T, R> Set<R> toSet(Iterable<T> iterable, Function<T, R> transformer) {
        return toSet(ForkJoinPool.commonPool(), iterable, transformer);
     }    
}
