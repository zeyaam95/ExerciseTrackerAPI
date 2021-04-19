package com.exercise.api.controller;

import com.exercise.api.model.User;
import com.exercise.api.model.UserModel;
import com.exercise.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping()
    private List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    private UserModel getUser(@PathVariable("id") long id) throws Exception {
        User targetUser = userService.getUserById(id);
        LocalDate dob = targetUser.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(dob, currentDate).getYears();
        UserModel myUser = new UserModel(
                targetUser.getUserName(),
                targetUser.getWeight(),
                targetUser.getHeight(),
                age,
                targetUser.getJoinDate());
        return myUser;
    }

    @PostMapping("/name/{userName}")
    private long getByUserName(@PathVariable("userName") String userName, @RequestBody String misc) throws Exception {
        if (misc.length() < 4) {
            throw new Exception("Password must be longer than 3 characters");
        }
        misc = misc.substring(0, misc.length()-1);
        User targetUser = userService.getUserByUserName(userName);
        if (!misc.equals(targetUser.getPassword())) {
            throw new Exception("Password does not match");
        }
        return targetUser.getUserId();
    }

    @DeleteMapping("{id}")
    private void deleteUser(@PathVariable("id") long id) throws Exception {
        userService.delete(id);
    }

    @PostMapping()
    private long saveUser(@RequestBody User user) throws Exception {
        userService.saveOrUpdate(user);
        return user.getUserId();
    }
}
