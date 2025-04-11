package com.microservice.gateway.config;

import java.util.List;
import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.http.HttpMethod;


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) throws Exception {
        // httpSecurity.authorizeHttpRequests()
        //         .requestMatchers("/actuator/**").permitAll()
        //         .anyRequest().authenticated()
        //         .and()
        //         .csrf().disable()
        //         .headers().frameOptions().disable();
        return httpSecurity
                    .csrf(ServerHttpSecurity.CsrfSpec::disable)
                    .authorizeExchange(exchange -> exchange
                    // configurar los endponits publicos
                        .pathMatchers(HttpMethod.GET, "/student/api/student/search/**").permitAll()
                        .pathMatchers("/course/api/course/search/**").permitAll()

                    // configurar los endpoints privados
                        .pathMatchers("/student/api/student/create").hasAuthority("CREATE")
                        .pathMatchers("/student/api/student/all").hasAuthority("READ")
                        .pathMatchers("/course/api/course/create").hasAuthority("CREATE")
                        .pathMatchers("/course/api/course/all").hasAuthority("READ")
                        .pathMatchers("/course/api/course/delete/**").hasAuthority("DELETE")
                        .pathMatchers("/student/api/student/search-by-course/**").hasRole("USER")
                        .pathMatchers("/course/api/course/search-students/**").hasRole("ADMIN")

                    // configurar los demas endpoints
                        .anyExchange().denyAll()

                    )
                    .httpBasic(Customizer.withDefaults())
                    .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
