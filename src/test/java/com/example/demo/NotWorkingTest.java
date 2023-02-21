package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotWorkingTest {
    @TestConfiguration
    @EnableWebSecurity
    static class SecurityConfig {
        @Bean
        public SecurityFilterChain configure(HttpSecurity http) throws Exception {
            return http.authorizeHttpRequests(customizer -> customizer
                    .requestMatchers("/demoservlet/**").permitAll()
            ).build();
        }
    }

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @Test
    void helloIsWorking() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        String uri = "http://localhost:" + port + "/demoservlet/hello";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);
        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }
}