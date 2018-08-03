package ru.serdar1980.condig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;


@EnableConfigurationProperties
@ConfigurationProperties("quiz.csv")
public class Csv {
    @NotNull
    private String filename;
    @NotNull
    private String cvsSplitBy;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCvsSplitBy() {
        return cvsSplitBy;
    }

    public void setCvsSplitBy(String cvsSplitBy) {
        this.cvsSplitBy = cvsSplitBy;
    }
}
