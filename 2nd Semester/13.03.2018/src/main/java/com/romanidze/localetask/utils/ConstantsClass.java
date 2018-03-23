package com.romanidze.localetask.utils;

import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

/**
 * 23.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class ConstantsClass {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public DateTimeFormatter getFormatter() {
        return this.formatter;
    }
}
