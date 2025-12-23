package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        // Spring context loads without error
    }

    @Test
    void timestampAfterTest() {
        Instant nowInstant = Instant.now();
        Instant oneHourAgo = nowInstant.minusSeconds(3600);

        Timestamp tsNow = Timestamp.from(nowInstant);
        Timestamp tsOneHourAgo = Timestamp.from(oneHourAgo);

        assertTrue(tsNow.after(tsOneHourAgo), "tsNow should be after tsOneHourAgo");
    }

    @Test
    void stringToLongConversionTest() {
        String strId = "12345";
        Long id = Long.valueOf(strId);

        assertEquals(12345L, id);
    }
}
