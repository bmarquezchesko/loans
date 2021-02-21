package com.example.loans.repository;

import com.example.loans.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findById(Long id);

    User save(User user);

    void deleteById(Long id);
}
