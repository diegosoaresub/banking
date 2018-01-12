package com.bank.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author diego.rodrigues
 *
 */
@Configuration
public class ServicesContext {
    @Bean
    public AccountService accountService() {
        return new AccountServiceImpl();
    }
}
