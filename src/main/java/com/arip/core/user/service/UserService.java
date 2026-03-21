package com.arip.core.user.service;

import com.arip.core.observability.metrics.UserMetrics;
import com.arip.core.user.dto.CreateUserRequest;
import com.arip.core.user.dto.PagedResponse;
import com.arip.core.user.dto.UpdateUserRequest;
import com.arip.core.user.dto.UserResponse;
import com.arip.core.user.entity.User;
import com.arip.core.user.exception.EmailAlreadyExistsException;
import com.arip.core.user.exception.UserNotFoundException;
import com.arip.core.user.mapper.PagedMapper;
import com.arip.core.user.mapper.UserMapper;
import com.arip.core.user.repository.UserRepository;
import com.arip.core.user.utils.UpdateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PagedMapper pagedMapper;
    private final UserMetrics userMetrics;

    public UserService(UserRepository userRepository, UserMapper userMapper, PagedMapper pagedMapper, UserMetrics userMetrics) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.pagedMapper = pagedMapper;
        this.userMetrics = userMetrics;
    }

    public UserResponse createUser(CreateUserRequest createUserRequest){

        log.info("Create User with Email : {} Name : {}",
                createUserRequest.getEmail(),
                createUserRequest.getName());


        if(userRepository.existsByEmail(createUserRequest.getEmail())){
            throw new EmailAlreadyExistsException(createUserRequest.getEmail());
        }

        User user = new User();
        user.setName(createUserRequest.getName());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(createUserRequest.getPassword());
        user.setActive(true);

        User userSaved = userRepository.save(user);

        userMetrics.incrementUserCreated();
        return userMapper.response(userSaved);
    }

    public PagedResponse<UserResponse> getUsers(Pageable pageable){

        log.info("Get Users with Pageable format Page Number : {}, Offset : {}, Sort : {}, Page Size : {}",
                pageable.getPageNumber(),
                pageable.getOffset(),
                pageable.getSort(),
                pageable.getPageSize());


        Page<User> usersPage = userRepository.findAll(pageable);
        return pagedMapper.pagedResponse(usersPage,userMapper::response);
    }

    public UserResponse getUserById(UUID id){

        log.info("Get User by Id : {}",
                id);

        User userSaved = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.response(userSaved);
    }

    public UserResponse updateUser(UUID id, UpdateUserRequest request){

        log.info("Update User by Id : {}, name : {}, email : {}, active : {}",
                id,
                request.getName(),
                request.getEmail(),
                request.getActive());


        User userSaved = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if(request.getEmail() != null &&
                userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        UpdateUtils.applyIfNotNull(request.getEmail(), userSaved::setEmail);
        UpdateUtils.applyIfNotNull(request.getPassword(), userSaved::setPassword);
        UpdateUtils.applyIfNotNull(request.getName(), userSaved::setName);
        UpdateUtils.applyIfNotNull(request.getActive(), userSaved::setActive);


//  for learning purpose
//        Optional.ofNullable(request.getEmail())
//                .ifPresent(user::setEmail);
//
//        Optional.ofNullable(request.getPassword())
//                .ifPresent(user::setPassword);
//
//        Optional.ofNullable(request.getActive())
//                .ifPresent(user::setActive);
//
//        Optional.ofNullable(request.getName())
//                .ifPresent(user::setName);

        User updatedUser = userRepository.save(userSaved);

        userMetrics.incrementUserUpdated();
        return userMapper.response(updatedUser);
    }

    public void deleteUser(UUID id){

        log.info("Delete User by id : {}",
                id);


        User userSaved = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(userSaved);
        userMetrics.incrementUserDeleted();
    }


}
