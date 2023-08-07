package az.atl.msauth.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static az.atl.msauth.enums.Permission.*;
import static az.atl.msauth.enums.Role.AGENT;
import static az.atl.msauth.enums.Role.SUPERVISOR;
import static org.springframework.http.HttpMethod.*;

@Configuration
public class SecurityConfig {

    private final AuthenticationProvider provider;
    private final JwtFilter filter;

    public SecurityConfig(AuthenticationProvider provider, JwtFilter filter) {
        this.provider = provider;
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/auth/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/api-docs/**",
                        "/swagger-ui.html"
                        ).permitAll()

                .requestMatchers("/supervisor/profile/**").hasRole(SUPERVISOR.name())
//                .requestMatchers("/supervisor/profile").hasRole(SUPERVISOR.name())
                .requestMatchers("/agent/profile/**").hasAnyRole(AGENT.name(), SUPERVISOR.name())
//                .requestMatchers("/agent/profile").hasAnyRole(AGENT.name(), SUPERVISOR.name())

                .requestMatchers(GET, "/supervisor/profile/**").hasAuthority(SUPERVISOR_READ.name())
//                .requestMatchers(GET,"/supervisor/profile").hasAuthority(SUPERVISOR_READ.name())
                .requestMatchers(GET, "/agent/profile/**").hasAnyAuthority(AGENT_READ.name(), SUPERVISOR_READ.name())

                .requestMatchers(DELETE, "/supervisor/profile/**").hasAuthority(SUPERVISOR_DELETE.name())
                .requestMatchers(DELETE, "/agent/profile/**").hasAnyAuthority(AGENT_DELETE.name(), SUPERVISOR_DELETE.name())

                .requestMatchers(PUT, "/supervisor/profile/**").hasAuthority(SUPERVISOR_UPDATE.name())
                .requestMatchers(PUT, "/agent/profile/**").hasAnyAuthority(AGENT_UPDATE.name(), SUPERVISOR_UPDATE.name())

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(provider)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
