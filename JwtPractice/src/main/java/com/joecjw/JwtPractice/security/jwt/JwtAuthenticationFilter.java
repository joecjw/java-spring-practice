package com.joecjw.JwtPractice.security.jwt;

import com.joecjw.JwtPractice.exception.InvalidJwtTokenException;
import com.joecjw.JwtPractice.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsServiceImpl userDetailsImpl;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userNameEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer")){
            //No authorization activity can be performed, pass to next filter
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }

        //Extract JWT token
        jwtToken = authHeader.substring(7);

        if(JwtService.extractAllClaims(jwtToken) == null){
            //Invalid token field
            SecurityContextHolder.getContext().setAuthentication(null);
            throw new InvalidJwtTokenException("Invalid Token");
        }

        //Extract username and user email from JWT token
        userNameEmail = jwtService.extractUserNameEmail(jwtToken);

        if(userNameEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //authenticate the user with unique username and email combination
            UserDetails userDetails = userDetailsImpl.loadUserByUsername(userNameEmail);

            if(jwtService.isJwtTokenValid(jwtToken, userDetails)){
                //create authentication token object contains the userDetails and authority info of the user
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                //set extra details from the request
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //update SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        //pass to next filter
        filterChain.doFilter(request,response);
    }
}
