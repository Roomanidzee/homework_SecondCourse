package com.romanidze.springcontexttask.logic.implementations;

import com.romanidze.springcontexttask.logic.interfaces.GELCurrency;

public class GELCurrencyImpl implements GELCurrency {

    private double multiplier = 0.043;

    @Override
    public Double convert(double originalCurrency) {
        return originalCurrency * this.multiplier;
    }

}
