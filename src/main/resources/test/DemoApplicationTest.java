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

        // Use after() method for Timestamp comparison
        assertTrue(tsNow.after(tsOneHourAgo), "tsNow should be after tsOneHourAgo");
    }

    @Test
    void multipleTimestampsTest() {
        Instant start = Instant.now();
        Instant end = start.plusSeconds(600);

        Timestamp tsStart = Timestamp.from(start);
        Timestamp tsEnd = Timestamp.from(end);

        assertTrue(tsEnd.after(tsStart), "End timestamp should be after start timestamp");
    }

    @Test
    void stringToLongConversionTest() {
        String strId = "12345";
        Long id = Long.valueOf(strId);

        assertEquals(12345L, id);
    }

    @Test
    void complexTimestampTest() {
        Instant t1 = Instant.now();
        Instant t2 = t1.plusSeconds(3600);
        Instant t3 = t1.minusSeconds(1800);

        Timestamp ts1 = Timestamp.from(t1);
        Timestamp ts2 = Timestamp.from(t2);
        Timestamp ts3 = Timestamp.from(t3);

        assertTrue(ts2.after(ts1), "ts2 should be after ts1");
        assertTrue(ts1.after(ts3), "ts1 should be after ts3");
    }

}
