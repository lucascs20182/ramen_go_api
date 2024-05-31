package br.com.redventures.ramen_go.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(CsrfConfigurer::disable)
        .authorizeHttpRequests(requests -> requests
            .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR)
              .permitAll()
            .requestMatchers("/**")
              .permitAll())
        .build();
  }

}
