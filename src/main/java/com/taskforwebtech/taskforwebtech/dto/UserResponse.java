package com.taskforwebtech.taskforwebtech.dto;

import lombok.Builder;

@Builder
public record UserResponse(String username,
                          String password,
                          String email,
                          String phoneNumber,
                          String role)
{ }
