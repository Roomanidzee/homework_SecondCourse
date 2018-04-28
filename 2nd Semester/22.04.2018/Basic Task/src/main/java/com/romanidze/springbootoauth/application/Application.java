package com.romanidze.springbootoauth.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 27.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@SpringBootApplication
@ComponentScan("com.romanidze.springbootoauth")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
