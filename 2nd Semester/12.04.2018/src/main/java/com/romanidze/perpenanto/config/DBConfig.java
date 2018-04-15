package com.romanidze.perpenanto.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.romanidze.perpenanto.repositories"})
public class DBConfig {

    private final Environment environment;

    @Autowired
    public DBConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource(){

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(this.environment.getProperty("db.driver"));
        dataSource.setUrl(this.environment.getProperty("db.url"));
        dataSource.setUsername(this.environment.getProperty("db.username"));
        dataSource.setPassword(this.environment.getProperty("db.password"));

        return dataSource;
    }

    @Bean
    public Properties additionalProperties() {

        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        properties.setProperty("hibernate.show_sql", "true");
        return properties;

    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        entityManager.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManager.setPackagesToScan("com.romanidze.perpenanto.domain.user");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setJpaProperties(additionalProperties());

        return entityManager;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public JavaMailSender javaMailSender(){

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.environment.getProperty("mail.host"));
        mailSender.setPort(Integer.parseInt(this.environment.getProperty("mail.port")));
        mailSender.setUsername(this.environment.getProperty("mail.username"));
        mailSender.setPassword(this.environment.getProperty("mail.password"));
        mailSender.setProtocol(this.environment.getProperty("mail.protocol"));

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", this.environment.getProperty("mail.smtp.starttls.enable"));
        javaMailProperties.put("mail.smtp.allow8bitmime", this.environment.getProperty("mail.smtp.allow8bitmime"));
        javaMailProperties.put("mail.smtp.ssl.trust", this.environment.getProperty("mail.smtp.ssl.trust"));
        javaMailProperties.put("mail.smtp.auth", this.environment.getProperty("mail.smtp.auth"));
        javaMailProperties.put("mail.debug", this.environment.getProperty("mail.debug"));

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;

    }

}
