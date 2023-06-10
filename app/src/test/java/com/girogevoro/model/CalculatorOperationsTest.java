package com.girogevoro.model;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

public class CalculatorOperationsTest {
    private static final double DELTA = 1e-15;
    private CalculatorOperations calculator;
    private CalculatorOperations calculator2;

    @Before
    public void init() {
        calculator = new CalculatorOperations();
    }

    @Test
    public void test() {
        assertEquals(7, calculator.add(1, 6), DELTA);

        assertEquals(-5, calculator.sub(1, 6), DELTA);

        assertEquals(21, calculator.mul(3, 7), DELTA);

        assertEquals(3, calculator.div(21, 7), DELTA);

        assertNotEquals(0, calculator.add(1, 4), DELTA);
        assertArrayEquals(new Double[]{1d, 2d, 3d},
                new Double[]{
                        calculator.add(2, -1),
                        calculator.sub(3, 1),
                        calculator.mul(1, 3)});
        assertNull(calculator2);
        assertNotNull(calculator);
        calculator2 = calculator;
        assertSame(calculator, calculator2);

    }

}