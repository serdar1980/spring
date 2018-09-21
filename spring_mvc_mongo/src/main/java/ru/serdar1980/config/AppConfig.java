package ru.serdar1980.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableConfigurationProperties
@ConfigurationProperties()
@EnableMongoRepositories
@ComponentScan
public class AppConfig {
}
