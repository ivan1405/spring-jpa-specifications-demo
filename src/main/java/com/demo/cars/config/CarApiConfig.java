package com.demo.cars.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.demo.cars.dao.repository")
@EntityScan(basePackages = "com.demo.cars.dao")
@ComponentScan(basePackages = {"com.demo.cars"})
public class CarApiConfig {
}