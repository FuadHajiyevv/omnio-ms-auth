package az.atl.msauth.config.security;

import az.atl.msauth.dao.repository.TokenRepository;
import az.atl.msauth.service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtService service;

    private final TokenRepository tokenRepository;

    public JwtFilter(UserDetailsService userDetailsService, JwtService service, TokenRepository tokenRepository) {
        this.userDetailsService = userDetailsService;
        this.service = service;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String headerAuthorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (headerAuthorization == null || !headerAuthorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = headerAuthorization.substring(7);
        String username = service.getUsernameFromJwt(jwt);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            boolean isTokenValid = tokenRepository.findByToken(jwt)
                    .map(o -> !o.getExpired() && !o.getRevoked()).orElse(false);

            if (service.isValid(jwt, userDetails) && isTokenValid) {
                if (service.checkRole(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationObject = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    WebAuthenticationDetails authDetails = new WebAuthenticationDetails(request);
                    authenticationObject.setDetails(authDetails);
                    SecurityContextHolder.getContext().setAuthentication(authenticationObject);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
