package com.midash;

import org.slf4j.Logger;

public class TechnicalException extends RuntimeException {

    public TechnicalException(String message, Throwable cause, Logger logger) {        
        super(message, cause);
        logger.error(message, cause);
    }
    
    public TechnicalException(String message, Logger logger) {        
        super(message);
        logger.error(message);
    }
}
