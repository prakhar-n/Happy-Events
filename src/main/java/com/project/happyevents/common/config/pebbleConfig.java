package com.project.happyevents.common.config;

import com.mitchellbosecke.pebble.PebbleEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class pebbleConfig {

    @Bean
    public PebbleEngine pebbleEngine() {
        return new PebbleEngine.Builder().build();
    }
}
