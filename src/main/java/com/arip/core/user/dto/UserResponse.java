package com.arip.core.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UserResponse {

    private UUID id;

    private String name;

    private String email;

    private boolean active;

    private LocalDateTime createdAt;
}
