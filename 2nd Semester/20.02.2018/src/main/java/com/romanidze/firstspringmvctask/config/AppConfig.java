package com.romanidze.firstspringmvctask.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 20.02.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Configuration
@EnableWebMvc
@ComponentScan({"com.romanidze.firstspringmvctask.controllers", "com.romanidze.firstspringmvctask.services",
                "com.romanidze.firstspringmvctask.repositories", "com.romanidze.firstspringmvctask.config"})
@PropertySource("classpath:db.properties")
public class AppConfig implements ApplicationContextAware {

    private ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }

}
