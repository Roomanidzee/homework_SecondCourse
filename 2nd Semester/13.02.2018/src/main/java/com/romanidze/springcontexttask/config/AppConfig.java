package com.romanidze.springcontexttask.config;

import com.romanidze.springcontexttask.logic.implementations.BYNCurencyImpl;
import com.romanidze.springcontexttask.logic.implementations.GELCurrencyImpl;
import com.romanidze.springcontexttask.logic.implementations.USDCurrencyImpl;
import com.romanidze.springcontexttask.logic.interfaces.BYNCurrency;
import com.romanidze.springcontexttask.logic.interfaces.GELCurrency;
import com.romanidze.springcontexttask.logic.interfaces.USDCurrency;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.romanidze.springcontexttask")
public class AppConfig {

    @Bean
    public USDCurrency usdCurrency(){
        return new USDCurrencyImpl();
    }

    @Bean
    public GELCurrency gelCurrency(){
        return new GELCurrencyImpl();
    }

    @Bean
    public BYNCurrency bynCurrency(){
        return new BYNCurencyImpl();
    }

}
