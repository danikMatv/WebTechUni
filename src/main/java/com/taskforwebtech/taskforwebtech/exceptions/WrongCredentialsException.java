package com.taskforwebtech.taskforwebtech.exceptions;

import com.taskforwebtech.taskforwebtech.dto.UpdateUserRequest;
import com.taskforwebtech.taskforwebtech.dto.UserRequest;
import lombok.Getter;

@Getter
public class WrongCredentialsException extends RuntimeException {

    private final String title;
    private final Object credentials;

    public WrongCredentialsException(String title, UserRequest credentials) {
        this.title = title;
        this.credentials = credentials;
    }

    public WrongCredentialsException(String title, UpdateUserRequest credentials) {
        this.title = title;
        this.credentials = credentials;
    }

    public WrongCredentialsException(String title, String credentials) {
        this.title = title;
        this.credentials = credentials;
    }
}
