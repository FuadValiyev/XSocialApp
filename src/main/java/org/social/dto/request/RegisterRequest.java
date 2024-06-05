package org.social.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class RegisterRequest {
    private String username;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthday;
    private String password;
}