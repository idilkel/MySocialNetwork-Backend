package com.jb.MySocialNetwork.config;

import com.jb.MySocialNetwork.security.Information;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class MapConfig {
    @Bean
    public Map<UUID, Information> map() {
        return new HashMap<>();
    }
}
