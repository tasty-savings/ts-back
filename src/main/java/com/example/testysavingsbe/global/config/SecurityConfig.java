package com.example.testysavingsbe.global.config;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${server_host.front_url}")
    String frontUrl;

    private final CustomUserService customUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .requestMatchers("/recipe/original/**").permitAll()
                .requestMatchers("/recipe/share/**").permitAll()
                .requestMatchers("/healthcheck/userinfo").authenticated()
                .requestMatchers("/recipe/**").authenticated()
                .requestMatchers("/userinfo/**").authenticated()
                .anyRequest().permitAll()
            )
            .oauth2Login(oauth2 -> oauth2
                .redirectionEndpoint(endpoint -> endpoint
                    .baseUri("/login/oauth2/code")
                )
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                    .userService(customUserService)
                )
                .successHandler((request, response, authentication) -> {
                    request.getSession(true);
                    response.sendRedirect(frontUrl);         // baseurl
                    response.setHeader("Set-Cookie",
                        "JSESSIONID=" + authentication.getCredentials().toString());
                })
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")      // {baseurl 설정}
                .permitAll()
            );

        return http.build();
    }
}

