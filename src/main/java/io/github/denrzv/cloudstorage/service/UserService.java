package io.github.denrzv.cloudstorage.service;

import io.github.denrzv.cloudstorage.advice.ResourceNotFoundException;
import io.github.denrzv.cloudstorage.entity.User;
import io.github.denrzv.cloudstorage.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Data
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserId " + id + " not found"));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new ResourceNotFoundException("username " + username + " is not found!"));
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}


