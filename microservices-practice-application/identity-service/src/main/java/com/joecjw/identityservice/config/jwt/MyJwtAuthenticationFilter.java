package com.joecjw.identityservice.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joecjw.identityservice.config.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyJwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsImpl;

    private static final Logger logger = LoggerFactory.getLogger(MyJwtAuthenticationFilter.class);

    private void handleFilterException(@NonNull HttpServletRequest request,
                                       @NonNull HttpServletResponse response,
                                       String errorMessage) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", errorMessage);
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String jwtToken = request.getHeader("Authorization");

        if (jwtToken == null){
            //miss authorization header
            logger.error("Missing Authorization Header");
            SecurityContextHolder.getContext().setAuthentication(null);
            handleFilterException(request, response, "Missing Authorization Header");
            return;
        }

        if(jwtUtils.extractAllClaims(jwtToken) == null){
            //Invalid token field
            logger.error("Invalid Token");
            SecurityContextHolder.getContext().setAuthentication(null);
            handleFilterException(request, response, "Invalid Token");
            return;
        }

        //Extract username and user email from JWT token
        final String userNameEmail = jwtUtils.extractUserNameEmail(jwtToken);

        if(userNameEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //authenticate the user with unique username and email combination
            UserDetails userDetails = userDetailsImpl.loadUserByUsername(userNameEmail);

            if(jwtUtils.isJwtTokenValid(jwtToken, userDetails)){
                //create authentication token object contains the userDetails and authority info of the user
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                //set extra details from the request
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //update SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }else {
                logger.error("user password not match record in database");
                SecurityContextHolder.getContext().setAuthentication(null);
                handleFilterException(request, response, "user password not match record in database");
                return;
            }
        }else {
            logger.error("userNameEmail is null or current Authentication is not null");
            SecurityContextHolder.getContext().setAuthentication(null);
            handleFilterException(request, response, "userNameEmail is null or current Authentication is not null");
            return;
        }

        //pass to next filter
        filterChain.doFilter(request,response);
    }
}