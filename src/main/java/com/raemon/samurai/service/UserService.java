package com.raemon.samurai.service;

import com.raemon.samurai.dto.UserRequest;
import com.raemon.samurai.dto.UserResponse;
import com.raemon.samurai.exceptionhandler.UserNotFoundException;
import com.raemon.samurai.model.User;
import com.raemon.samurai.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserResponse(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail()))
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        return new UserResponse(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail());
    }

    public UserResponse createUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setEmail(userRequest.getEmail());
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail());
    }


    public UserResponse updateUser(Integer id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setEmail(userRequest.getEmail());
        // Update other user properties as needed

        user = userRepository.save(user);

        return new UserResponse(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail());
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        userRepository.delete(user);
    }

}
