package io.github.denrzv.cloudstorage.repository;

import io.github.denrzv.cloudstorage.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
