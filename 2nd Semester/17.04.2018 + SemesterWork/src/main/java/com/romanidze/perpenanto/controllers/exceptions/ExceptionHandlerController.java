package com.romanidze.perpenanto.controllers.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 21.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ModelAndView getErrorPage(HttpServletResponse resp, Exception ex){

        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("status", resp.getStatus());
        modelAndView.addObject("error", ex.getCause().toString());
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.addObject("timestamp", localDateTime.format(formatter));
        return modelAndView;

    }


}
