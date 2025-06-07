package dev.mzcy.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/wiki", "/wiki/", "/wiki/{id}", "/wiki/random", "/wiki/search").permitAll()  // öffentlich
                        .requestMatchers("/wiki/**").authenticated() // alle anderen (POST, PUT, DELETE) require auth
                )
                .httpBasic(Customizer.withDefaults());  // Basic Auth

        return http.build();
    }
}
