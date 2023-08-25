package com.joecjw.JwtPractice.auth;

import com.joecjw.JwtPractice.entity.RefreshToken;
import com.joecjw.JwtPractice.entity.Role;
import com.joecjw.JwtPractice.entity.User;
import com.joecjw.JwtPractice.exception.UsernameEmailNotFoundException;
import com.joecjw.JwtPractice.repository.UserRepository;
import com.joecjw.JwtPractice.security.jwt.JwtService;
import com.joecjw.JwtPractice.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

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
        jwtResponse.setJwtToken(JwtService.generateJwtToken(_user));
        jwtResponse.setEmail(_user.getEmail());
        jwtResponse.setUsername(user.getUsername().substring(0,user.getUsername().indexOf(":")));
        jwtResponse.setRoles(roles);
        return jwtResponse;
    }

    public JwtResponse login(AuthenticationRequest request) {
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

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = JwtService.generateJwtToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        List<String> roles = user
                                .getAuthorities()
                                .stream()
                                .map(item -> item.getAuthority())
                                .collect(Collectors.toList());


        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setUserId(user.getId());
        jwtResponse.setType("Bearer");
        jwtResponse.setJwtToken(jwtToken);
        jwtResponse.setRefreshToken(refreshToken.getToken());
        jwtResponse.setEmail(user.getEmail());
        jwtResponse.setUsername(user.getUsername().substring(0,user.getUsername().indexOf(":")));
        jwtResponse.setRoles(roles);

        return jwtResponse;
    }
}
