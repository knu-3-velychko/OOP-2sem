package com.lab3.demo.converter;

import com.lab3.demo.dto.UserDTO;
import com.lab3.demo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserConverterTest {
    private final String email = "email@gmail.com";
    private final String name = "Name";
    private final String surname = "Surname";

    @InjectMocks
    private UserConverter userConverter;
    @Mock
    private User user;
    @Mock
    private UserDTO userDTO;

    @Test
    public void convertToDto() {
        when(user.getEmail()).thenReturn(email);
        when(user.getName()).thenReturn(name);
        when(user.getSurname()).thenReturn(surname);

        UserDTO userDTO = userConverter.convertToDto(user);

        assertEquals(email, userDTO.getEmail());
        assertEquals(name, userDTO.getName());
        assertEquals(surname, userDTO.getSurname());
    }

    @Test
    public void convertToEntity() {
        when(userDTO.getEmail()).thenReturn(email);
        when(userDTO.getName()).thenReturn(name);
        when(userDTO.getSurname()).thenReturn(surname);

        User user = userConverter.convertToEntity(userDTO);

        assertEquals(email, user.getEmail());
        assertEquals(name, user.getName());
        assertEquals(surname, user.getSurname());
    }
}