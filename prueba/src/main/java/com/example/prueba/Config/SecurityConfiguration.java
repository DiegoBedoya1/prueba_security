package com.example.prueba.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JWTFilter filter;

    public SecurityConfiguration(JWTFilter filter) {
        this.filter = filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
         http
                .csrf(AbstractHttpConfigurer::disable)
                 .sessionManagement(session ->
                         session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .authorizeHttpRequests(auth -> auth
                         .requestMatchers(
                                 "/auth/**",
                                 "/users/new",
                                 "/swagger-ui/**",
                                 "/v3/api-docs/**",
                                 "/actuator/health",
                                 "/actuator/info"
                         ).permitAll()

                         .requestMatchers(
                                 "/products/new",
                                 "/products/update/**",
                                 "/orders/state/**",
                                 "/orders/confirm/**",
                                 "/orders/delivered/**",
                                 "/actuator/metrics/**"
                         ).hasRole("ADMIN")

                         .requestMatchers(
                                 "/products/availables",
                                 "/orders/new",
                                 "/products/all/**"
                         ).hasAnyRole("ADMIN", "CLIENTE")

                         .requestMatchers(
                                 "/orders/all/**",
                                 "/orders/cancel/**"
                         ).hasAnyRole("ADMIN", "CLIENTE")

                         .anyRequest().authenticated()
                 )
                 .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

}






/*{
        "user": {
        "id": "9"
        },
        "details": [
        {
        "product": {
        "id": 1
        },
        "quantity": 2
        },
        {
        "product": {
        "id": 3
        },
        "quantity": 1
        }
        ]
        }*/