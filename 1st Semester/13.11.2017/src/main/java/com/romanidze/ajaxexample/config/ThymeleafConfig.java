package com.romanidze.ajaxexample.config;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 14.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@WebListener
public class ThymeleafConfig implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce){

        TemplateEngine engine = this.templateEngine(sce.getServletContext());
        TemplateEngineUtil.storeTemplateEngine(sce.getServletContext(), engine);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    private TemplateEngine templateEngine(ServletContext sc){

        TemplateEngine engine = new TemplateEngine();

        engine.setTemplateResolver(this.templateResolver(sc));

        return engine;

    }

    private ITemplateResolver templateResolver(ServletContext sc){

        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(sc);

        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(true);

        return resolver;
    }

}

