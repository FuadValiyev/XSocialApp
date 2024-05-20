package org.social.dto.request;

import org.social.entities.User;

import java.time.LocalDate;

public record UserRequest(String username,
                          String name,
                          String surname,
                          String email,
                          String password,
                          LocalDate birthday) {

    public static User convertUserRequestToUser(UserRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.username);
        user.setSurname(userRequest.surname());
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());
        user.setBirthday(userRequest.birthday());
        return user;
    }

}
