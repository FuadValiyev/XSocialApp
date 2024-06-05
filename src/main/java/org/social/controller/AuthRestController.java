package org.social.controller;

import lombok.RequiredArgsConstructor;
import org.social.dto.request.LoginRequest;
import org.social.dto.request.RegisterRequest;
import org.social.entities.User;
import org.social.repository.UserRepository;
import org.social.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            return new ResponseEntity<>(jwtUtil.generateToken(
                    loginRequest.getEmail(),
                    userRepository.findByEmail(loginRequest.getEmail()).getUserId(),
                    userRepository.findByEmail(loginRequest.getEmail()).getName() +
                            " " + userRepository.findByEmail(loginRequest.getEmail()).getSurname()
            )
                    , HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {

        if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
            return new ResponseEntity<>("Email already exist", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setSurname(registerRequest.getSurname());
        user.setBirthday(registerRequest.getBirthday());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getPassword())
        );
        return new ResponseEntity<>(jwtUtil.generateToken(
                registerRequest.getEmail(),
                userRepository.findByEmail(registerRequest.getEmail()).getUserId(),
                registerRequest.getName() + " " + registerRequest.getSurname()
        )
                , HttpStatus.OK
        );
    }
}
