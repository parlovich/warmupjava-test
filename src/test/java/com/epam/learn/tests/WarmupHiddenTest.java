package com.epam.learn.tests;

import com.epam.learn.TemperatureConverter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for Warmup task.
 */
public class WarmupHiddenTest
{
    private static final TemperatureConverter converter = new TemperatureConverter();

    @Test
    public void hiddenTest()
    {
        assertEquals(50, converter.toFahrenheit(10));
    }
}
