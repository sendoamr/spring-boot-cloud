package com.sendoa.opendata.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.web.WebAppConfiguration;

@Configuration
@EnableAutoConfiguration
@WebAppConfiguration
@ComponentScan(basePackages = {"com.sendoa.opendata"})
public class TestConfig {
}