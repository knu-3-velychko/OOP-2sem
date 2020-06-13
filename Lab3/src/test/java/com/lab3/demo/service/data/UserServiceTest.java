package com.lab3.demo.service.data;

import com.lab3.demo.entity.User;
import com.lab3.demo.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Mock
    private User user;

    @Before
    public void before() {
        String email = "email@gmail.com";
        when(user.getEmail()).thenReturn(email);
    }

    @Test
    public void find() {
        userService.findUserByEmail(user.getEmail());
        verify(userRepository).findByEmail(user.getEmail());
    }
}
