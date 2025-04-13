package com.taskforwebtech.taskforwebtech.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateUserRequest {

    private String userName;
    private String password;
    private String phoneNumber;

}
