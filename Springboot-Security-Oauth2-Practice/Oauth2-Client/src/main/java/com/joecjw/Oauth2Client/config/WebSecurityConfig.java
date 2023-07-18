package com.joecjw.Oauth2Client.config;

import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2ClientConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private static final String[] WHITE_LIST_URLS = {
            "/api/hello",
            "/api/register",
            "/api/verifyRegistration*",
            "/api/resendVerifyToken*"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                //.requestMatchers(WHITE_LIST_URLS).permitAll()
                .requestMatchers("/api/**").authenticated()
                .and()
                .oauth2Login(oauth2login ->
                        oauth2login.loginPage("http//:localhost:9000/oauth2/authorization/api-client-oidc"))
                .oauth2Client(Customizer.withDefaults());

        return http.build();
    }
}
