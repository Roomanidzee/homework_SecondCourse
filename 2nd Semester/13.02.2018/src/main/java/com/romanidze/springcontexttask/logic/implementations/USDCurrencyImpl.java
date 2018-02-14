package com.romanidze.springcontexttask.logic.implementations;

import com.romanidze.springcontexttask.logic.interfaces.USDCurrency;

public class USDCurrencyImpl implements USDCurrency {

    private double multiplier = 0.017;

    @Override
    public Double convert(double originalCurrency) {
        return originalCurrency * this.multiplier;
    }

}
