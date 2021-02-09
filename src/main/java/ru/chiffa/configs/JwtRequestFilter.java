package ru.chiffa.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.chiffa.services.UserService;
import ru.chiffa.utils.JwtTokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                Claims jwt = jwtTokenUtil.getClaimsFromToken(authHeader.substring(7));
                String username = jwt.getSubject();
                List<GrantedAuthority> roles = userService.mapRoleNamesToAuthorities(jwt.get("roles", List.class));

                if (username != null && roles != null) {
                    UsernamePasswordAuthenticationToken token =
                            new UsernamePasswordAuthenticationToken(username,null, roles);

                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            } catch (ExpiredJwtException e) {
                //Jwt is expired
            }
        }

        filterChain.doFilter(request, response);


//        String username = null;
//        String jwt = null;
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            jwt = authHeader.substring(7);
//            try {
//                username = jwtTokenUtil.getUsernameFromToken(jwt);
//            } catch (ExpiredJwtException e) {
//                //Jwt is expired
//            }
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UsernamePasswordAuthenticationToken token =
//                    new UsernamePasswordAuthenticationToken(
//                            username,
//                            null,
//                            userService.mapRoleNamesToAuthorities(jwtTokenUtil.getRoles(jwt)));
//            SecurityContextHolder.getContext().setAuthentication(token);
//        }


    }
}
