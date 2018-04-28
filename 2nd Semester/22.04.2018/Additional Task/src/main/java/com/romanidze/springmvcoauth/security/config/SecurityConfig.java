package com.romanidze.springmvcoauth.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 28.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@ComponentScan("com.romanidze.springmvcoauth")
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
              .antMatchers("/login").permitAll()
              .antMatchers("/user_page").authenticated()
              .anyRequest().permitAll()
            .and()
              .csrf()
              .disable();

    }

}
