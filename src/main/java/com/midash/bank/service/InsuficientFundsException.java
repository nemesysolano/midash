package com.midash.bank.service;

import org.apache.log4j.Logger;

import com.midash.bank.MidashException;

public class InsuficientFundsException extends MidashException{

    public InsuficientFundsException(String message,  Logger logger) {
        super(message, logger);
    }
    
}
