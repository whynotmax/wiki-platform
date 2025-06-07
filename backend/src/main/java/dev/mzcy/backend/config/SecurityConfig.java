package dev.mzcy.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                            "/wiki", 
                            "/wiki/", 
                            "/wiki/{id}", 
                            "/wiki/random", 
                            "/wiki/search",
                            "/wiki/root",
                            "/wiki/{id}/sub"
                        ).permitAll()  // öffentlich
                        .requestMatchers("/wiki/**").authenticated() // alle anderen (POST, PUT, DELETE) require auth
                )
                .httpBasic(Customizer.withDefaults());  // Basic Auth

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // Allow requests from frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
            }
        };
    }
}
