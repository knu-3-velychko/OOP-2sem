package com.lab3.demo.service;

import com.lab3.demo.converter.UserConverter;
import com.lab3.demo.dto.UserDTO;
import com.lab3.demo.entity.User;
import com.lab3.demo.service.data.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationControllerService {
    private final UserConverter userConverter;
    private final RegistrationService registrationService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public UserDTO save(UserDTO userDTO) {
        User currentUser = userConverter.convertToEntity(userDTO);
        UserDTO savedUserDto = userConverter.convertToDto(registrationService.save(currentUser));
        savedUserDto.setPassword(userDTO.getPassword());
        applicationEventPublisher.publishEvent(savedUserDto);
        return savedUserDto;
    }
}
