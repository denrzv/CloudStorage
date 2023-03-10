package io.github.denrzv.cloudstorage.service;

import io.github.denrzv.cloudstorage.entity.User;
import io.github.denrzv.cloudstorage.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + login);
        }
        return new org.springframework.security.core.userdetails.User(user.get().getLogin(), user.get().getPassword(),
                new ArrayList<>());
    }
}
