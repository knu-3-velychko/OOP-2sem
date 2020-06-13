package com.lab3.demo.service.data;

import com.lab3.demo.entity.User;
import com.lab3.demo.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private User user;

    @Test
    public void save() {
        registrationService.save(user);

        verify(userRepository).findByEmail(user.getEmail());
        verify(userRepository).save(any());
    }
}
