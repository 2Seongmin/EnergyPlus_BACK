package com.kh.ecolog.configuration;
import org.springframework.http.HttpMethod; // 꼭 추가!
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfigure {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) 
            .authorizeHttpRequests(auth -> 
                auth
	                .requestMatchers(HttpMethod.POST, "/markets/**").permitAll()
	                .requestMatchers(HttpMethod.PUT, "/markets/**").permitAll() // ← 요거 추가
                	.requestMatchers("/members/**").permitAll()

                  .requestMatchers("/markets/**").permitAll()

                	.requestMatchers("/notices/**").permitAll()
                                   
                  .requestMatchers("/apis/**").permitAll() 
                	
                	.requestMatchers("/uploads/**", "/resources/**", "/css/**", "/js/**", "/images/**").permitAll()

                  .anyRequest().authenticated() 
            )
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable);
        
        return http.build();
    }
}
