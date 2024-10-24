package com.aps.projetocarros.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class ConfigSeguranca {

    private final UserDetailsService servicoDetalhesUsuario;

    public ConfigSeguranca(UserDetailsService servicoDetalhesUsuario) {
        this.servicoDetalhesUsuario = servicoDetalhesUsuario;
    }

    @Bean
    public SecurityFilterChain filtroDeSeguranca(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .requestMatchers("/api/carros/**").authenticated()
            .requestMatchers("/api/usuarios/**").permitAll()
            .anyRequest().permitAll()
            .and()
            .httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder codificadorSenha() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager gerenciadorAutenticacao(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder construtorGerenciadorAutenticacao = http
            .getSharedObject(AuthenticationManagerBuilder.class);

        construtorGerenciadorAutenticacao
            .userDetailsService(servicoDetalhesUsuario)
            .passwordEncoder(codificadorSenha());

        return construtorGerenciadorAutenticacao.build();
    }
}
