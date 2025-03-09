package org.example.project_managment_app.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JWTTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/auth/signup") || path.startsWith("/auth/signin")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = request.getHeader("Authorization");


        if (jwt != null) {
            jwt = jwt.substring(7);

            try {
                SecretKey secretKey = Keys.hmacShaKeyFor(JWTConstant.JWT_SECRET.getBytes());

                Claims claims = Jwts.parser().setSigningKey(secretKey).build().parseSignedClaims(jwt).getPayload();

                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication auth = new UsernamePasswordAuthenticationToken(email, null, grantedAuthorities);

                SecurityContextHolder.getContext().setAuthentication(auth);

            }catch (Exception e) {
                throw new BadCredentialsException("Invalid token");
            }
        }

        filterChain.doFilter(request, response);
    }
}
