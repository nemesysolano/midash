package com.midash.bank.service;

import org.slf4j.Logger;

import com.midash.BusinessException;

public class EntityNotFoundException extends BusinessException{
    String entityName;

    public EntityNotFoundException(String entityName,  Logger logger) {
        super(entityName, logger);
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }
}
