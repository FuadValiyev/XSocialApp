package org.social.controller;

import lombok.RequiredArgsConstructor;
import org.social.dto.request.UserRequest;
import org.social.dto.response.UserResponse;
import org.social.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    @GetMapping("/users")
    public List<UserResponse> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/{username}")
    public UserResponse getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/creat")
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PutMapping("/{username}")
    public UserResponse updateUser(@PathVariable String username,
                                   @RequestBody UserRequest requestBody) {
        return userService.updateUserByUsername(username, requestBody);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        if (userService.deleteUserByUsername(username)) {
            return ResponseEntity.ok("user deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("an error");
        }
    }

    @GetMapping("/isFollowing")
    public ResponseEntity<Boolean> isFollowing(@RequestParam Long userId, @RequestParam Long followId) {
        return new ResponseEntity<>(userService.isFollowing(userId, followId), HttpStatus.OK);
    }

}
