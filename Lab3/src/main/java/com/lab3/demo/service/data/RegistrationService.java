package com.lab3.demo.service.data;

import com.lab3.demo.entity.User;
import com.lab3.demo.exception.UserAlreadyExistsException;
import com.lab3.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;

    @Transactional
    public User save(User currentUser) {
        Optional<User> oldUser = userRepository.findByEmail(currentUser.getEmail());
        return oldUser.orElseGet(() -> userRepository.save(currentUser));
    }
}
