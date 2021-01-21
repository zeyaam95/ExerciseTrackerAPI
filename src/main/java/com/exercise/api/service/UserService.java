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

    public User getUserById(long id) { return userRepository.findById(id).get();}

    public void saveOrUpdate(User user) { userRepository.save(user); }

    public void delete(long id) { userRepository.deleteById((id)); }
}
