package com.midash.bank;

import org.slf4j.Logger;

public class MidashException extends RuntimeException {

    public MidashException(String message, Throwable cause, Logger logger) {        
        super(message, cause);
        logger.error(message, cause);
    }
    
    public MidashException(String message, Logger logger) {        
        super(message);
        logger.error(message);
    }
}
