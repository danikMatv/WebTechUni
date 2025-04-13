package com.taskforwebtech.taskforwebtech.service;

import com.taskforwebtech.taskforwebtech.entity.User;
import com.taskforwebtech.taskforwebtech.repository.UserRepository;
import com.taskforwebtech.taskforwebtech.security.UsersDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User with " + userName + " not found");
        }

        return new UsersDetails(user);
    }
}