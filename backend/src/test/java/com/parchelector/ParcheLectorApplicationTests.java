package com.parchelector;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Basic smoke test to verify that the Spring Boot context loads correctly.
 * 
 * This test uses an in-memory H2 database instead of PostgreSQL so it can
 * run in CI environments (like GitHub Actions) without needing a real DB.
 * 
 * @author Julian Colmenares
 */
@SpringBootTest
@TestPropertySource(properties = {
        // Override datasource for tests
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=update"
})
class ParcheLectorApplicationTests {

    @Test
    void contextLoads() {
        // If the application context starts without throwing exceptions,
        // this test is considered successful.
    }
}
