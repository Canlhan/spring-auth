package com.spring_auth.spring_auth.config;

import com.spring_auth.spring_auth.dao.UserRepository;
import com.spring_auth.spring_auth.service.UserManager;
import com.spring_auth.spring_auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig
{

    private final UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain configure(HttpSecurity htpp) throws Exception{

        htpp.httpBasic(Customizer.withDefaults());
        htpp.csrf().disable().authorizeHttpRequests((auth)->
                auth.
                    requestMatchers("/api/v1/register").permitAll()
                    .anyRequest().authenticated()

        ).userDetailsService(userDetailsService())

        ;

        return htpp.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){

        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }




}
