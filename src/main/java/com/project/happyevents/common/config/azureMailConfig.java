package com.project.happyevents.common.config;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class azureMailConfig {
    @Bean

    public EmailClient emailClient() {
        return new EmailClientBuilder()
                .connectionString("//enterConnectionString")
                .buildClient();
    }
}
