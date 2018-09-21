package ru.serdar1980.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.web.WebAppConfiguration;


@Configuration
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class AppTestConfig {
}
