package org.social.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.social.dto.request.UserRequest;
import org.social.dto.response.UserResponse;
import org.social.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserRestController userRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers() {
        List<UserResponse> users = new ArrayList<>();
        when(userService.getAllUser()).thenReturn(users);

        List<UserResponse> result = userRestController.getAllUser();

        assertEquals(users, result);
    }

    @Test
    void getUserByUsername() {
        UserResponse user = new UserResponse("username", "name", "surname", "email", LocalDate.now());
        when(userService.getUserByUsername(anyString())).thenReturn(user);

        UserResponse result = userRestController.getUserByUsername("username");

        assertEquals(user, result);
    }

    @Test
    void createUser() {
        UserRequest request = new UserRequest("username", "name", "surname", "email", "password", LocalDate.now());
        UserResponse response = new UserResponse("username", "name", "surname", "email", LocalDate.now());
        when(userService.createUser(any())).thenReturn(response);

        UserResponse result = userRestController.createUser(request);

        assertEquals(response, result);
    }

    @Test
    void updateUserByUsername() {
        UserRequest request = new UserRequest("name", "surname", "email", "test@test.com", "password", LocalDate.now());
        UserResponse response = new UserResponse("username", "name", "surname", "email", LocalDate.now());
        when(userService.updateUserByUsername(anyString(), any())).thenReturn(response);

        UserResponse result = userRestController.updateUser("username", request);

        assertEquals(response, result);
    }

    @Test
    void deleteUserByUsername() {
        when(userService.deleteUserByUsername(anyString())).thenReturn(true);

        ResponseEntity<String> result = userRestController.deleteUserByUsername("username");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("user deleted successfully", result.getBody());
    }
}
