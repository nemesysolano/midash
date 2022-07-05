package com.midash.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class BaseTest {
    private ObjectMapper mapper = new ObjectMapper(); 
    public String toJSON(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);       
    } 
    
    public static String getCurrentMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return stackTrace[2].getMethodName();
    }
}