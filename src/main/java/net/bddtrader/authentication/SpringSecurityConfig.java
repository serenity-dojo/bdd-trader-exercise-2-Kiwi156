package net.bddtrader.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}password")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}password")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeHttpRequests(request -> {
                            request.requestMatchers(HttpMethod.GET, "/api/client/**").hasRole("USER");
                            request.requestMatchers(HttpMethod.POST, "/api/client/**").hasRole("USER");
                            request.requestMatchers(HttpMethod.PUT, "/api/client/**").hasRole("USER");
                            request.requestMatchers(HttpMethod.PATCH, "/api/client/**").hasRole("USER");
                            request.requestMatchers(HttpMethod.DELETE, "/api/client/**").hasRole("USER");
                            request.requestMatchers("api/**").permitAll();

                        }
                )
                .csrf().disable()
                .formLogin().disable();

        return http.build();
    }

}
