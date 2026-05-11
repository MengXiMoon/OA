package com.oa.common;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void shouldCreateSuccessResult() {
        Result<String> result = Result.success("hello");
        assertEquals(200, result.getCode());
        assertEquals("success", result.getMessage());
        assertEquals("hello", result.getData());
    }

    @Test
    void shouldCreateErrorResult() {
        Result<Void> result = Result.error(500, "server error");
        assertEquals(500, result.getCode());
        assertEquals("server error", result.getMessage());
        assertNull(result.getData());
    }
}
