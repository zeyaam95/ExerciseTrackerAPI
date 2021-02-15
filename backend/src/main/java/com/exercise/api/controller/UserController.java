package com.exercise.api.controller;

import com.exercise.api.model.User;
import com.exercise.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping()
    private List<User> getAllActivities() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    private User getActivity(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("{id}")
    private void deleteActivity(@PathVariable("id") long id) {
        userService.delete(id);
    }

    @PostMapping()
    private long saveActivity(@RequestBody User user) {
        userService.saveOrUpdate(user);
        return user.getUserId();
    }
}
