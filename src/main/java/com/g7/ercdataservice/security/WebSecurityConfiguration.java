package com.g7.ercdataservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${auth.api.url}")
    private String authServiceURL;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() {
        return null;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //.addFilterAfter(new JWTAuthorizationFilter(authServiceURL), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                //.antMatchers("/api/data/test/message").permitAll()
                //.antMatchers("/api/data/test/message/secure/method/**").authenticated()
                //.antMatchers("/api/data/test/message/secure").hasAnyAuthority("ROLE_REVIEWER")
                .anyRequest().permitAll();//authenticated();
    }
}
