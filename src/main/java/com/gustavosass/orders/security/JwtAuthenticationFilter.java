package com.gustavosass.orders.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.gustavosass.orders.exceptions.ExceptionResponse;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        List<String> allowedPaths = List.of(
            "/api/v1/auth",
            "/v3/api-docs",
            "/swagger-ui",
            "/swagger-ui.html"
        );

        boolean isAllowedPath = allowedPaths.stream().anyMatch(request.getServletPath()::startsWith);

        if (isAllowedPath) {
            filterChain.doFilter(request, response);
            return;
        }
        
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            ExceptionResponse exceptionResponse = new ExceptionResponse(
                "Bad header format", "Authorization is blank or not in Bearer format" 
            );
            response.getWriter().write(exceptionResponse.toString());
            return;
        }
        
        jwt = authHeader.substring(7);
        
        try {
            // Extrai o username e verifica se o token é válido
            userEmail = jwtService.extractUsername(jwt);
            
            // Se extraiu o email e não há autenticação no contexto, verifica
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                
                // Valida o token apenas verificando assinatura e expiração
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            ExceptionResponse exceptionResponse = new ExceptionResponse(
                "Failed to authenticate",
                e.getMessage()
            );
            response.getWriter().write(exceptionResponse.toString());
            return;
        }
        
        filterChain.doFilter(request, response);
    }
}