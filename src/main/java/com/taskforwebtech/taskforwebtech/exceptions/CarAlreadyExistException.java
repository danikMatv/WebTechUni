package com.taskforwebtech.taskforwebtech.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class CarAlreadyExistException extends RuntimeException {
    private final String car;
    private final String message;
}
