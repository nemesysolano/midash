package com.midash;

import org.slf4j.Logger;

public class BussinessException extends RuntimeException{

    public BussinessException(String message, Throwable cause, Logger logger) {        
        super(message, cause);
        logger.error(message, cause);
    }
    
    public BussinessException(String message, Logger logger) {        
        super(message);
        logger.error(message);
    }
    
}
