package com.joecjw.apigateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.HashMap;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private RestTemplate restTemplate;

    public static class Config{}

    public JwtAuthenticationFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            //request endpoint is secured or not
            if(routeValidator.isSecured.test(exchange.getRequest())){

                HttpHeaders headers = exchange.getRequest().getHeaders();

                System.out.println();
                //header contains token or not
                if (!headers.containsKey("Authorization")) {
                    System.out.println("error_1");
                    throw new RuntimeException("Missing authorization header");
                }

                String authHeaderContent = headers.get("Authorization").get(0);
                System.out.println(headers.get("Authorization").get(0));
                if (authHeaderContent == null || !authHeaderContent.startsWith("Bearer ")) {
                    System.out.println("error_2");
                    throw new RuntimeException("Invalid Authorization Header Content");
                }

                String jwtToken = authHeaderContent.substring(7);

                //make REST call to identity-service for Authorization Activity
                RequestEntity<?> request = new RequestEntity<>(HttpMethod.GET,URI.create("http://identity-service//authenticate"));
                request.getHeaders().set("Authorization", jwtToken);
                ResponseEntity<?> authResponse = restTemplate.exchange(request, ResponseEntity.class);

                //authorization success or not
                if (authResponse.getStatusCode() != HttpStatus.OK){
                    throw new RuntimeException(authResponse.getBody().toString());
                }

                HashMap<String, Object> userDetails = (HashMap<String, Object>) authResponse.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                String userData;
                try {
                    userData = objectMapper.writeValueAsString(userDetails);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
                exchange.getRequest().mutate().header("userDetails", userData);
            }
            return chain.filter(exchange);
        });
    }
}