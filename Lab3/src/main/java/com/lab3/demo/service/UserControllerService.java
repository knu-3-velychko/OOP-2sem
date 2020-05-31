package com.lab3.demo.service;

import com.lab3.demo.dto.UserDTO;
import com.lab3.demo.entity.User;
import com.lab3.demo.service.data.UserService;
import com.lab3.demo.converter.UserConverter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserControllerService {
    private final UserService userService;
    private final UserConverter userConverter;

    public List<UserDTO> findByEmail(String email) {
        List<UserDTO> userDTOList = new ArrayList<>();
        Optional<User> user = userService.findUserByEmail(email);
        user.ifPresent(value -> userDTOList.add(userConverter.convertToDto(user.get())));

        return userDTOList;
    }
}
