package com.taskforwebtech.taskforwebtech.service;


import com.taskforwebtech.taskforwebtech.dto.AuthenticationRequest;
import com.taskforwebtech.taskforwebtech.dto.AuthenticationResponse;
import com.taskforwebtech.taskforwebtech.dto.TokenResponse;
import com.taskforwebtech.taskforwebtech.dto.UserRequest;
import com.taskforwebtech.taskforwebtech.entity.User;
import com.taskforwebtech.taskforwebtech.exceptions.UserNotFoundException;
import com.taskforwebtech.taskforwebtech.exceptions.WrongCredentialsException;
import com.taskforwebtech.taskforwebtech.mapper.UserMapper;
import com.taskforwebtech.taskforwebtech.security.JwtService;
import com.taskforwebtech.taskforwebtech.security.UsersDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final UserService userService;

    @Transactional
    public AuthenticationResponse register(UserRequest registerRequest) {

        User savedUser = userService.save(registerRequest);

        TokenResponse tokenResponse = getTokenResponse(savedUser);
        return new AuthenticationResponse(userMapper.mapToUserResponse(savedUser), tokenResponse);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userService.findByUserName(request.getUserName());
        if(user == null) {
            throw new UserNotFoundException("User with " + request.getUserName() + " not found");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new WrongCredentialsException("incorrect password", "password or phone number is incorrect");
        }

        TokenResponse tokenResponse = getTokenResponse(user);
        return new AuthenticationResponse(userMapper.mapToUserResponse(user), tokenResponse);
    }

    private TokenResponse getTokenResponse(User user) {
        UsersDetails userDetails = new UsersDetails(user);
        var jwtToken = jwtService.generateToken(userDetails);
        long currentTime = System.currentTimeMillis();
        long expirationTime = currentTime + (1000 * 60 * 60 * 24);
        return new TokenResponse(jwtToken, currentTime, expirationTime);
    }

}