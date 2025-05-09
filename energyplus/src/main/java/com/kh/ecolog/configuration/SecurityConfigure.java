package com.kh.ecolog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kh.ecolog.configuration.filter.JwtFilter;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfigure {

    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .formLogin(config -> config.disable())
            .httpBasic(config -> config.disable())
            .csrf(config -> config.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
            	    // 인증 없이 허용
            	    .requestMatchers("/auth/**", "/oauth2/**").permitAll()
            	    .requestMatchers(HttpMethod.GET, "/admin/cardnews", "/admin/cardnews/**").permitAll()
            	    .requestMatchers("/admin/cardnews/list").permitAll()

            	    // 관리자 - 마켓 게시글 관리
            	    .requestMatchers(HttpMethod.PUT, "/admin/market/hide/**").hasAuthority("ROLE_ADMIN")
            	    .requestMatchers(HttpMethod.GET, "/admin/market/**").hasAuthority("ROLE_ADMIN")
            	    .requestMatchers(HttpMethod.PUT, "/admin/market/**").hasAuthority("ROLE_ADMIN")
            	    .requestMatchers(HttpMethod.DELETE, "/admin/market/**").hasAuthority("ROLE_ADMIN")

            	    // 카드뉴스
            	    .requestMatchers(HttpMethod.POST, "/admin/cardnews/**").hasAuthority("ROLE_ADMIN")
            	    .requestMatchers(HttpMethod.PUT, "/admin/cardnews/**").hasAuthority("ROLE_ADMIN")
            	    .requestMatchers(HttpMethod.DELETE, "/admin/cardnews/**").hasAuthority("ROLE_ADMIN")

            	    // 관리자 전체
            	    .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // 항상 마지막!

            	    // 일반 인증
            	    .requestMatchers(HttpMethod.DELETE, "/members/withdrawal").authenticated()
            	    .requestMatchers(HttpMethod.PUT, "/info/pass").authenticated()
            	    .requestMatchers("/promise/me").authenticated()

            	    // 공개 경로
            	    .requestMatchers(
            	        "/members/**", "/markets/**", "/notices/**", "/apis/**",
            	        "/uploads/**", "/resources/**", "/css/**", "/js/**", "/images/**",
            	        "/qnas/**", "/replys/**", "/challenges/**", "/test/**", "/promise/**",
            	        "/api/verification/**", "/mymarket/**", "/info/**", "/info/grade/**",
            	        "/mymile/**", "/totalmile/**", "/totalcategory/**"
            	    ).permitAll()

            	    .anyRequest().authenticated()
            	)
            .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}