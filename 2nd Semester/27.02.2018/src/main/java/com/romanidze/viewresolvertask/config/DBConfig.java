package com.romanidze.viewresolvertask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * 03.03.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Configuration
public class DBConfig {

    private final Environment environment;

    @Autowired
    public DBConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean("simpleJDBC")
    @Profile("live")
    public DataSource dataSource(){

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(this.environment.getProperty("db.driver"));
        dataSource.setUrl(this.environment.getProperty("db.url"));
        dataSource.setUsername(this.environment.getProperty("db.username"));
        dataSource.setPassword(this.environment.getProperty("db.password"));

        return dataSource;

    }

   /*
     здесь описание другого DataSource с другим профилем
     @Bean
     @Profile("dev")
   */

}
