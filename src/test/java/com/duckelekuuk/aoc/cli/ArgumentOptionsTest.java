package com.duckelekuuk.aoc.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentOptionsTest {

    @Test
    void get() {
        // Arrange
        String[] args = {"--key=value"};
        ArgumentOptions argumentOptions = new ArgumentOptions(args);

        // Act

        // Assert
        assertEquals("value", argumentOptions.get("--key"));
        assertNull(argumentOptions.get("404"));
        assertNull(argumentOptions.get(null));
    }

    @Test
    void containsKey() {
        // Arrange
        String[] args = {"--key=value"};
        ArgumentOptions argumentOptions = new ArgumentOptions(args);

        // Act

        // Assert
        assertTrue(argumentOptions.containsKey("--key"));
        assertFalse(argumentOptions.containsKey("404"));
        assertFalse(argumentOptions.containsKey(null));
    }
}