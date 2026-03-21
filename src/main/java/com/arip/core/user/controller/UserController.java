package com.arip.core.user.controller;

import com.arip.core.user.dto.CreateUserRequest;
import com.arip.core.user.dto.PagedResponse;
import com.arip.core.user.dto.UpdateUserRequest;
import com.arip.core.user.dto.UserResponse;
import com.arip.core.user.service.UserService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.created(null).body(response);
    }

    @GetMapping
    public ResponseEntity<PagedResponse<UserResponse>> getUsers(Pageable pageable){
        PagedResponse<UserResponse> response = userService.getUsers(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@Valid @PathVariable("id") UUID id){
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@Valid @PathVariable("id") UUID id, @Valid @RequestBody UpdateUserRequest request){
        UserResponse response = userService.updateUser(id,request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@Valid @PathVariable("id") UUID id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
