package com.midash.bank;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Order;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
public class ContextInitializationTest extends BaseServiceTest{
    static Logger logger = Logger.getLogger(ContextInitializationTest.class);
    
    @Autowired
    DataSource dataSource;

    @Test
    @Order(1)
    public void isDataSourceInitialized() {
        assertNotNull(dataSource);
    }

    @Test
    @Order(2)
    public void isDatabaseAvailable() throws SQLException {
        dataSource.getConnection().close();
    }    
}
