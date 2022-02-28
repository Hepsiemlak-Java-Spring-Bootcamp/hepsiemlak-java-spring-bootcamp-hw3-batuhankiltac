package com.emlakburada.emlakburada.service;

import com.emlakburada.emlakburada.dto.request.UserRequest;
import com.emlakburada.emlakburada.dto.response.UserResponse;
import com.emlakburada.emlakburada.model.User;
import com.emlakburada.emlakburada.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse convertToUserResponse(User savedUser) {
        UserResponse response = new UserResponse();
        response.setUserId(savedUser.getUserId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        return response;
    }

    public User convertToUser(UserRequest request) {
        User user = new User();
        user.setUserId(request.getUserId());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return user;
    }

    public UserResponse createUser(UserRequest userRequest) {
        User savedUser = userRepository.saveUser(convertToUser(userRequest));
        return convertToUserResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        List<UserResponse> userList = new ArrayList<>();
        for (User user : userRepository.findAllUsers()) {
            userList.add(convertToUserResponse(user));
        }
        return userList;
    }

    public UserResponse getUserById(int userId) {
        User user = userRepository.findUserById(userId);
        return convertToUserResponse(user);
    }

    public UserResponse getUserFavoritesById(int id) {
        return convertToUserResponse(userRepository.findUserFavoritesById(id));
    }
}