package com.elyashevich.core.filter;

import com.elyashevich.core.exception.InvalidTokenException;
import com.elyashevich.core.util.TokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.elyashevich.core.util.TokenConstantUtil.*;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var authHeader = request.getHeader(HEADER_NAME);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        var jwt = authHeader.substring(BEARER_PREFIX.length());
        String email = null;
        try {
            email = TokenUtil.extractEmailClaims(jwt);
            request.setAttribute("email", email);
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token");
        }
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var context = SecurityContextHolder.createEmptyContext();
            var authToken = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    TokenUtil.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).toList()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authToken);
            SecurityContextHolder.setContext(context);
        }
        filterChain.doFilter(request, response);
    }
}
