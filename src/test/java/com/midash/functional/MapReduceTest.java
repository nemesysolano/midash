package com.midash.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapReduceTest {
    
    @Test
    public void toSetWithCollection() {
        Set<String> threads = MapReduce.toSet(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), n -> {
            return Thread.currentThread().getName();
        });
     
        assertTrue(threads.size() > 0); //REMARK: Breaks in single processor machines
        log.debug(threads.toString()+ " , " + threads.size());
    }

    @Test
    public void toSetWithIterableEven() {
        List<Integer> list = new LinkedList<>();        
        Iterable<Integer> iterable = list;
        IntStream.range(0, Runtime.getRuntime().availableProcessors()).forEach(n -> list.add(n));
        
        Set<Integer> fromList = MapReduce.toSet(list, n -> n + 1);
        Set<Integer> fromIterable = MapReduce.toSet(iterable, n -> n + 1);

        assertEquals(fromList, fromIterable);

    }    

    @Test
    public void toSetWithIterableOddGreater() {
        List<Integer> list = new LinkedList<>();        
        Iterable<Integer> iterable = list;
        IntStream.range(0, Runtime.getRuntime().availableProcessors()+1).forEach(n -> list.add(n));
        
        Set<Integer> fromList = MapReduce.toSet(list, n -> n + 1);
        Set<Integer> fromIterable = MapReduce.toSet(iterable, n -> { try { Thread.sleep(5); } catch(Throwable t){}; return n + 1;});

        assertEquals(fromList, fromIterable);

    }     
    
    @Test
    public void toSetWithIterableOddLower() {
        List<Integer> list = new LinkedList<>();        
        Iterable<Integer> iterable = list;
        IntStream.range(0, Runtime.getRuntime().availableProcessors()-1).forEach(n -> list.add(n));
        
        Set<Integer> fromList = MapReduce.toSet(list, n -> n + 1);
        Set<Integer> fromIterable = MapReduce.toSet(iterable, n -> { try { Thread.sleep(5); } catch(Throwable t){}; return n + 1;});

        assertEquals(fromList, fromIterable);

    }     
}
