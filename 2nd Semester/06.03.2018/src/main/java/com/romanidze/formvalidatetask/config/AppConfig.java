package com.romanidze.formvalidatetask.config;

import de.neuland.jade4j.Jade4J;
import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.spring.template.SpringTemplateLoader;
import de.neuland.jade4j.spring.view.JadeViewResolver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 09.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Configuration
@EnableWebMvc
@ComponentScan({"com.romanidze.formvalidatetask.config", "com.romanidze.formvalidatetask.controllers",
                "com.romanidze.formvalidatetask.repositories", "com.romanidze.formvalidatetask.services",
                "com.romanidze.formvalidatetask.utils","com.romanidze.formvalidatetask.validators"})
@PropertySource("classpath:db.properties")
public class AppConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ApplicationContext appContext;
    private final String ENCODING = "UTF-8";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        registry.addResourceHandler("/css/**")
                .addResourceLocations("/css/")
                .setCachePeriod(3600)
                .resourceChain(true);

    }

    @Bean
    public SpringTemplateLoader templateLoader() {
        SpringTemplateLoader templateLoader = new SpringTemplateLoader();
        templateLoader.setBasePath("/WEB-INF/templates/");
        templateLoader.setEncoding(this.ENCODING);
        templateLoader.setSuffix(".pug");
        return templateLoader;
    }

    @Bean
    public JadeConfiguration jadeConfiguration() {
        JadeConfiguration configuration = new JadeConfiguration();
        configuration.setCaching(false);
        configuration.setPrettyPrint(true);
        configuration.setTemplateLoader(templateLoader());
        configuration.setMode(Jade4J.Mode.HTML);
        return configuration;
    }

    @Bean
    public ViewResolver viewResolver() {
        JadeViewResolver viewResolver = new JadeViewResolver();
        viewResolver.setConfiguration(jadeConfiguration());
        viewResolver.setRenderExceptions(true);
        viewResolver.setSuffix(".pug");
        return viewResolver;
    }

}
