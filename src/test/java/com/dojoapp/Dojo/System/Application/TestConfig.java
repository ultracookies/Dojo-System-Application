package com.dojoapp.Dojo.System.Application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.dojoapp.Dojo.System.Application.controller",
        "com.dojoapp.Dojo.System.Application.model"})
public class TestConfig {
}
