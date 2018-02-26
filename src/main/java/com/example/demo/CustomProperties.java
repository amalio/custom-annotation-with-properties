package com.example.demo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="my")
@Getter
@Setter
public class CustomProperties {
    private String prop;
}
