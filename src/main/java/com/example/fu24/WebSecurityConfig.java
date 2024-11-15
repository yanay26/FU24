package com.example.fu24;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Отключаем CSRF защиту
                .authorizeRequests()
                .requestMatchers("/login", "/register", "/css/**").permitAll() // Разрешаем доступ без аутентификации
                .anyRequest().authenticated() // Все другие запросы требуют аутентификации
                .and()
                .formLogin()
                .loginPage("/login") // Указываем страницу входа
                .permitAll() // Доступ к странице входа для всех
                .and()
                .logout()
                .permitAll(); // Доступ к выходу для всех

        return http.build(); // Возвращаем построенный SecurityFilterChain
    }
}
