package com.threadzy.app.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor 
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Check if the Authorization header is valid
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT and Username
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);

        // Process authentication if username exists and user isn't already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // Load user from database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Validate token against database user
            if (jwtService.isTokenValid(jwt, userDetails)) {
                
                // Create the Authentication Token for Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // We don't need the password anymore, the JWT is the proof
                        userDetails.getAuthorities()
                );

                // Add request details (like IP, session ID) to the token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Final Step: Put the user in the "Security Context"
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Always call the next filter in the chain!
        filterChain.doFilter(request, response);
    }
}
