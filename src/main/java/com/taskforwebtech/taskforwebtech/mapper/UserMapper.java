package com.taskforwebtech.taskforwebtech.mapper;

import com.taskforwebtech.taskforwebtech.dto.UpdateUserRequest;
import com.taskforwebtech.taskforwebtech.dto.UserRequest;
import com.taskforwebtech.taskforwebtech.dto.UserResponse;
import com.taskforwebtech.taskforwebtech.security.RegisterRequestMappingPasswordEncoder;
import org.mapstruct.*;
import com.taskforwebtech.taskforwebtech.entity.User;

@Mapper(componentModel = "spring",
        uses = RegisterRequestMappingPasswordEncoder.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserResponse mapToUserResponse(User newUser);

    @Mapping(target = "role", constant = "ROLE_USER")
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    User map(UserRequest userRequest);

    User updateUser(@MappingTarget User user, UpdateUserRequest userRequest);

}
