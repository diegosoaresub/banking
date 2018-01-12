package com.bank.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.bank.controller.AccountController;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(AccountController.class);
    }

}