package com.arip.core.user.mapper;

import com.arip.core.user.dto.UserResponse;
import com.arip.core.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse response(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setActive(user.isActive());
        userResponse.setCreatedAt(user.getCreatedAt());

        return userResponse;
    }
}
