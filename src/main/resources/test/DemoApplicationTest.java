package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoApplicationTest {

    @Test
    void contextLoads() {
        // Spring context load test
    }

    @Test
    void timestampComparisonTest() {
        // Instant → Timestamp conversion
        Instant nowInstant = Instant.now();
        Instant oneHourAgo = nowInstant.minusSeconds(3600);

        Timestamp tsNow = Timestamp.from(nowInstant);
        Timestamp tsOneHourAgo = Timestamp.from(oneHourAgo);

        // Timestamp comparison using after()
        assertTrue(tsNow.after(tsOneHourAgo), "tsNow should be after tsOneHourAgo");
    }

    @Test
    void stringToLongTest() {
        String strId = "12345";
        Long id = Long.valueOf(strId);

        assertEquals(12345L, id);
    }

    @Test
    void multipleTimestampTest() {
        Instant start = Instant.now();
        Instant end = start.plusSeconds(600);

        Timestamp tsStart = Timestamp.from(start);
        Timestamp tsEnd = Timestamp.from(end);

        assertTrue(tsEnd.after(tsStart), "End timestamp should be after start timestamp");
    }

}
