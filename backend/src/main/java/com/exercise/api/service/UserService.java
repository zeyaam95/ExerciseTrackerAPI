package com.exercise.api.service;

import com.exercise.api.model.User;
import com.exercise.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(user));
        return users;
    }

    public User getUserById(long id) throws Exception {
        if (id < 0) {
            throw new Exception("userId cannot be negative");
        }
        return userRepository.findById(id).get();
    }

    public User getUserByUserName(String userName) throws Exception {
        if (userName.length() == 0) {
            throw new Exception("userName cannot be an empty string");
        }
        return userRepository.findUserByUserName(userName);
    }

    public void saveOrUpdate(User user) throws Exception {
        if (user.getUserId() < 0) {
            throw new Exception("userId cannot be negative");
        }
        userRepository.save(user);
    }

    public void delete(long id) throws Exception {
        if (id < 0) {
            throw new Exception("userId cannot be negative");
        }
        userRepository.deleteById((id));
    }
}
