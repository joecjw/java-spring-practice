package com.joecjw.identityservice.service;

import com.joecjw.identityservice.config.jwt.JwtUtils;
import com.joecjw.identityservice.entity.Role;
import com.joecjw.identityservice.entity.User;
import com.joecjw.identityservice.exception.UsernameEmailNotFoundException;
import com.joecjw.identityservice.payload.JwtResponse;
import com.joecjw.identityservice.payload.LoginRequest;
import com.joecjw.identityservice.payload.RegisterRequest;
import com.joecjw.identityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse register(RegisterRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        switch (request.getRole()){
            case "user":
                user.setRole(Role.ROLE_USER);
                break;

            case "admin":
                user.setRole(Role.ROLE_ADMIN);;
                break;

            default:
                break;
        }

        User _user = userRepository.save(user);

        List<String> roles = _user
                .getAuthorities()
                .stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setUserId(_user.getId());
        jwtResponse.setType("Bearer");
        jwtResponse.setJwtToken(JwtUtils.generateJwtToken(_user));
        jwtResponse.setEmail(_user.getEmail());
        jwtResponse.setUsername(_user.getUsername());
        jwtResponse.setRoles(roles);
        return jwtResponse;
    }

    public JwtResponse login(LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUserNameEmail(), request.getPassword());
        authenticationManager.authenticate(authentication);

        String[] index = request.getUserNameEmail().split(":");
        String firstName = index[0].split(" ")[0].trim();
        String lastName = index[0].split(" ")[1].trim();
        String email = index[1].trim();

        User user = userRepository.findAllByFirstNameAndLastNameAndEmail(firstName, lastName, email)
                .orElseThrow(() -> new UsernameEmailNotFoundException(
                        "User with Username= " + firstName + " " + lastName +
                                " And Email= " + email +" Not Found")
                );

        String jwtToken = JwtUtils.generateJwtToken(user);
        List<String> roles = user
                .getAuthorities()
                .stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setUserId(user.getId());
        jwtResponse.setType("Bearer");
        jwtResponse.setJwtToken(jwtToken);
        jwtResponse.setEmail(user.getEmail());
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setRoles(roles);

        return jwtResponse;
    }
}
