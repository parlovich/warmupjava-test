package com.epam.learn.tests;

import com.epam.learn.TemperatureConverter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for Warmup task.
 */
public class WarmupHiddenTest
{
    private static final TemperatureConverter converter = new TemperatureConverter();

    @Test
    public void cold() {
        assertEquals(-4, converter.toFahrenheit(-20));
    }

    @Test
    public void cool() {
        assertEquals(32, converter.toFahrenheit(0));
    }

    @Test
    public void medium() {
        assertEquals(59, converter.toFahrenheit(15));
    }

    @Test
    public void hot() {
        assertEquals(104, converter.toFahrenheit(40));
    }
}
