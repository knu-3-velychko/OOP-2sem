package com.lab3.demo.controller;

import com.lab3.demo.dto.UserDTO;
import com.lab3.demo.service.UserControllerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
public class UserController {
    private final UserControllerService userService;

    @GetMapping(value = "/user/{email}")
    public List<UserDTO> findUsers(@PathVariable String email) {
        return userService.findByEmail(email);
    }
}
