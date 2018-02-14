package com.romanidze.springcontexttask.logic.implementations;

import com.romanidze.springcontexttask.logic.interfaces.BYNCurrency;

public class BYNCurencyImpl implements BYNCurrency {

    private double multiplier = 0.034;

    @Override
    public Double convert(double originalCurrency) {
        return originalCurrency * this.multiplier;
    }
}
