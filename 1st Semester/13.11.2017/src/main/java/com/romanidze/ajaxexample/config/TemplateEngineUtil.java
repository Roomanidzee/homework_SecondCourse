package com.romanidze.ajaxexample.config;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import org.thymeleaf.TemplateEngine;

/**
 * 14.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@WebListener
public class TemplateEngineUtil {

    private static final String TEMPLATE_ENGINE_ATTR = "com.romanidze.perpenanto.config.TemplateEngineInstance";

    public static void storeTemplateEngine(ServletContext context, TemplateEngine engine){

        context.setAttribute(TEMPLATE_ENGINE_ATTR, engine);

    }

    public static TemplateEngine getTemplateEngine(ServletContext context){

        return (TemplateEngine) context.getAttribute(TEMPLATE_ENGINE_ATTR);

    }

}
