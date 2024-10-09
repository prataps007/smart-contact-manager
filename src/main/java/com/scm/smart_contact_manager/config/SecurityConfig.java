package com.scm.smart_contact_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

public class SecurityConfig {

    // user create and login using java code with in memory service

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails user1 = User
                .withUsername("admin123")
                .password("admin123")
                .roles("ADMIN","USER")
                .build();

        UserDetails user2 = User.withUsername("user123")
                .password("password")
                .build();

        var inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        return inMemoryUserDetailsManager;

    }

}
