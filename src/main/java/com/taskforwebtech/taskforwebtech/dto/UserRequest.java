package com.taskforwebtech.taskforwebtech.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest
{
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
}
