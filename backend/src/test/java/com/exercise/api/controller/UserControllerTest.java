package com.exercise.api.controller;

import com.exercise.api.model.User;
import com.exercise.api.model.UserModel;
import com.exercise.api.service.ExerciseService;
import com.exercise.api.service.UserService;
import com.exercise.api.service.WorkoutService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    int port;

    @MockBean
    private UserService userService;

    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper mapper;
    List<User> users;
    User userOne, userTwo;
    UserModel userModel;

    @BeforeEach
    void loadEntities() throws ParseException {
        this.mapper = new ObjectMapper();
        this.users = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date mainDOB = formatter.parse("01-Jan-1990");
        LocalDate dob = mainDOB.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.userOne = new User(1, "testName", "12345", mainDOB, 100, 100, mainDOB);
        this.userTwo = new User(2, "testName", "12345", mainDOB, 100, 100, mainDOB);
        this.users.add(userOne);
        this.users.add(userTwo);

        LocalDate currentDate = LocalDate.now();
        int age = Period.between(dob, currentDate).getYears();
        this.userModel = new UserModel(
                userOne.getUserName(),
                userOne.getWeight(),
                userOne.getHeight(),
                age,
                userOne.getJoinDate());
    }

    @Test
    void testGetAllUsers() {
        final String uri = "http://localhost:" + port + "/user/";
        when(userService.getAllUsers()).thenReturn(this.users);
        JsonNode result = restTemplate.getForEntity(uri, JsonNode.class).getBody();
        List<User> resultUsers = this.mapper.convertValue(result, new TypeReference<List<User>>() {});
        assertEquals(resultUsers, this.users);
    }

    @Test
    void testGetUser() throws Exception {
        final String uri = "http://localhost:" + port + "/user/1";
        when(userService.getUserById(anyLong())).thenReturn(this.userOne);
        JsonNode result = restTemplate.getForEntity(uri, JsonNode.class).getBody();
        UserModel resultUserModel = this.mapper.convertValue(result, UserModel.class);
        assertEquals(resultUserModel, this.userModel);
    }

    @Test
    void testDeleteUser() throws Exception {
        final String uri = "http://localhost:" + port + "/user/1";
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);
        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void testSaveUser() throws Exception {
        final String uri = "http://localhost:" + port + "/user/";
        User user = this.userOne;
        user.setUserName("Brian Shaw");
        long result = restTemplate.postForObject(uri, user, long.class);
        assertEquals(1, result);
    }

}