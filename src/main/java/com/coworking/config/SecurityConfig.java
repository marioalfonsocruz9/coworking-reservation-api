package com.coworking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.coworking.security.jwt.JwtAuthenticationFilter;
import com.coworking.util.ApiPaths;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth -> auth

                    .requestMatchers(
                            "/api/v1/auth/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/v3/api-docs/**")
                    .permitAll()
                    
                    //Spaces
                    .requestMatchers(HttpMethod.GET, ApiPaths.SPACES, ApiPaths.SPACES + "/**")
                    .hasAnyRole("USER", "ADMIN")

                    .requestMatchers(HttpMethod.POST, ApiPaths.SPACES)
                    .hasRole("ADMIN")

                    .requestMatchers(HttpMethod.PUT, ApiPaths.SPACES + "/**")
                    .hasRole("ADMIN")

                    .requestMatchers(HttpMethod.DELETE, ApiPaths.SPACES + "/**")
                    .hasRole("ADMIN")
                    
                    //Reservation
                    .requestMatchers(HttpMethod.POST, ApiPaths.RESERVATIONS)
                    .hasAnyRole("USER", "ADMIN")

                    .requestMatchers(HttpMethod.GET, ApiPaths.RESERVATIONS + "/me")
                    .hasRole("USER")

                    .requestMatchers(HttpMethod.GET, ApiPaths.RESERVATIONS)
                    .hasRole("ADMIN")

                    .requestMatchers(HttpMethod.PATCH, ApiPaths.RESERVATIONS + "/**")
                    .hasAnyRole("USER", "ADMIN")
                    
                    .requestMatchers("/api/v1/mock/**").permitAll()

                    .anyRequest()
                    .authenticated())
            
            .formLogin(form -> form.disable())

            .httpBasic(httpBasic -> httpBasic.disable())
        
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
