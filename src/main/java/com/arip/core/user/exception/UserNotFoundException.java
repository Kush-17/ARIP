package com.arip.core.user.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserNotFoundException extends ApiException {
    public UserNotFoundException(UUID id) {
        super("User not found with id: "+ id, HttpStatus.NOT_FOUND);
    }
}
