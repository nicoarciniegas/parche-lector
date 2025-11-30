package com.parchelector;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Basic smoke test to verify that the Spring Application context loads
 * properly.
 *
 * This test is required for CI/CD to ensure the backend builds and runs.
 *
 * @author Julian Colmenares
 */
@SpringBootTest
class ParcheLectorApplicationTests {

    @Test
    void contextLoads() {
        // If the application starts without errors, this test automatically passes.
    }
}
