package com.taskforwebtech.taskforwebtech.service;

import com.taskforwebtech.taskforwebtech.dto.UpdateUserRequest;
import com.taskforwebtech.taskforwebtech.dto.UserRequest;
import com.taskforwebtech.taskforwebtech.dto.UserResponse;
import com.taskforwebtech.taskforwebtech.entity.User;
import com.taskforwebtech.taskforwebtech.exceptions.UserNotFoundException;
import com.taskforwebtech.taskforwebtech.exceptions.WrongCredentialsException;
import com.taskforwebtech.taskforwebtech.mapper.UserMapper;
import com.taskforwebtech.taskforwebtech.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }


    @Transactional
    public UserResponse createNewUser(UserRequest userRequest) {
        User user = userMapper.map(userRequest);
        return userMapper.mapToUserResponse(userRepository.save(user));
    }

    @Transactional
    public User save(UserRequest registerRequest) {
        User user = userMapper.map(registerRequest);
        return userRepository.save(user);
    }

    public UserResponse getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::mapToUserResponse)
                .orElseThrow(() -> new UserNotFoundException("User with " + id + " not found"));
    }

    @Transactional
    public UserResponse update(Long id, UpdateUserRequest request) {
        User toUpdateUser = userRepository.findUserById(id);
        if(toUpdateUser == null) {
            throw new UserNotFoundException("User with " + id + " not found");
        }

        if (userRepository.existsByUsername(request.getUserName())) {
            throw new WrongCredentialsException("Phone number is already taken!", request);
        }

        User newUser = userRepository.save(userMapper.updateUser(toUpdateUser, request));
        return userMapper.mapToUserResponse(newUser);
    }

    public List<UserResponse> getAll() {
        return userRepository.getAll().stream()
                .map(user -> userMapper.mapToUserResponse(user))
                .collect(Collectors.toList());
    }
}
