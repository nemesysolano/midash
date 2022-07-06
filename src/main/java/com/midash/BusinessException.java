package com.midash;

import org.slf4j.Logger;

public class BusinessException extends RuntimeException{

    public BusinessException(String message, Throwable cause, Logger logger) {        
        super(message, cause);
        logger.error(message, cause);
    }
    
    public BusinessException(String message, Logger logger) {        
        super(message);
        logger.error(message);
    }
    
}
