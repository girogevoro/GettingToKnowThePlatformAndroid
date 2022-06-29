package com.girogevoro.model;

public class CalculatorOperations implements CalculatorModel {
    @Override
    public double add(double arg1, double arg2) {
        return arg1 + arg2;
    }

    @Override
    public double sub(double arg1, double arg2) {
        return arg1 - arg2;
    }

    @Override
    public double mul(double arg1, double arg2) {
        return arg1 * arg2;
    }

    @Override
    public double div(double arg1, double arg2) {
        if (arg2 == 0) {
            return Double.MAX_VALUE;
        }
        return arg1 / arg2;
    }
}
