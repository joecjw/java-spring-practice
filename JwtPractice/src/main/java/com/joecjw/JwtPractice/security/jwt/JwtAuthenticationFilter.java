package com.joecjw.JwtPractice.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joecjw.JwtPractice.exception.InvalidJwtTokenException;
import com.joecjw.JwtPractice.security.service.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsServiceImpl userDetailsImpl;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private void overWriteResponse(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", e.getMessage());
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userNameEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ") || authHeader.length() < 8){
            //No authorization activity can be performed, pass to next filter
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }

        //Extract JWT token
        jwtToken = authHeader.substring(7);
        try {
            if(JwtService.extractAllClaims(jwtToken) == null){
                //Invalid token field
                SecurityContextHolder.getContext().setAuthentication(null);
                return;
            }
        } catch (SignatureException e) {
                logger.error("Invalid JWT signature: {}", e.getMessage());
                overWriteResponse(request, response, new Exception(e.getMessage()));
            } catch (MalformedJwtException e) {
                logger.error("Invalid JWT token: {}", e.getMessage());
            overWriteResponse(request, response, new Exception(e.getMessage()));
            } catch (ExpiredJwtException e) {
                logger.error("JWT token is expired: {}", e.getMessage());
            overWriteResponse(request, response, new Exception(e.getMessage()));
            } catch (UnsupportedJwtException e) {
                logger.error("JWT token is unsupported: {}", e.getMessage());
            overWriteResponse(request, response, new Exception(e.getMessage()));
            } catch (IllegalArgumentException e) {
                logger.error("JWT claims string is empty: {}", e.getMessage());
            overWriteResponse(request, response, new Exception(e.getMessage()));
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
