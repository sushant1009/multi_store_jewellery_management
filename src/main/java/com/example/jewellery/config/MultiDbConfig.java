package com.example.jewellery.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class MultiDbConfig {

    @Bean(name = "puneDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.pune")
    public DataSource puneDataSource() {
        return new DriverManagerDataSource();
    }

    @Bean(name = "mumbaiDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mumbai")
    public DataSource mumbaiDataSource() {
        return new DriverManagerDataSource();
    }

    @Bean(name = "puneEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean puneEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("puneDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.example.jewellery.entity").persistenceUnit("pune").build();
    }

    @Bean(name = "mumbaiEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mumbaiEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("mumbaiDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.example.jewellery.entity").persistenceUnit("mumbai").build();
    }

    @Bean(name = "puneTransactionManager")
    public PlatformTransactionManager puneTransactionManager(
            @Qualifier("puneEntityManagerFactory") LocalContainerEntityManagerFactoryBean emf) {
        return new JpaTransactionManager(emf.getObject());
    }

    @Bean(name = "mumbaiTransactionManager")
    public PlatformTransactionManager mumbaiTransactionManager(
            @Qualifier("mumbaiEntityManagerFactory") LocalContainerEntityManagerFactoryBean emf) {
        return new JpaTransactionManager(emf.getObject());
    }
}
