package com.waffle.user.domain.model;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
public class SignInForm {

    private String email;
    private String password;

}
