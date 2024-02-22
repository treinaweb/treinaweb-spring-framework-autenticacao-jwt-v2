package br.com.treinaweb.twjobs.api.auth.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.treinaweb.twjobs.api.auth.dtos.UserRequest;
import br.com.treinaweb.twjobs.api.auth.dtos.UserResponse;
import br.com.treinaweb.twjobs.api.auth.mappers.UserMapper;
import br.com.treinaweb.twjobs.core.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthRestController {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserResponse register(@RequestBody @Valid UserRequest userRequest) {
        var user = userMapper.toUser(userRequest);
        var passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
    
}
