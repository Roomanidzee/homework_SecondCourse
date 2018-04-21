package com.romanidze.perpenanto.config;

import com.romanidze.perpenanto.domain.user.Product;
import com.romanidze.perpenanto.domain.user.Profile;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan("com.romanidze.perpenanto")
@PropertySource(value = "classpath:application.properties")
public class AppConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ApplicationContext appContext;
    private final String ENCODING = "UTF-8";

    private final Converter<String, Product> productConverter;
    private final Converter<String, Profile> profileConverter;

    @Autowired
    public AppConfig(@Qualifier("productConverter") Converter<String, Product> productConverter,
                     @Qualifier("profileConverter") Converter<String, Profile> profileConverter) {
        this.productConverter = productConverter;
        this.profileConverter = profileConverter;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        registry.addResourceHandler("/css/**", "/fonts/**", "/js/**")
                .addResourceLocations("/css/", "/fonts/", "/js/")
                .setCachePeriod(3600)
                .resourceChain(true);

    }

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry){
        formatterRegistry.addConverter(this.productConverter);
        formatterRegistry.addConverter(this.profileConverter);
    }

    private ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(appContext);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(this.ENCODING);
        return resolver;
    }

    @Bean
    public TemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding(this.ENCODING);
        return resolver;
    }

}
