package com.arip.core.user.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends ApiException {
    public EmailAlreadyExistsException(String email) {
        super("Email Already Exists with emailId: "+ email, HttpStatus.CONFLICT);
    }
}
