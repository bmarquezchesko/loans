package com.example.loans.repository;

import com.example.loans.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findById(Long id);

    User save(User user);

    void deleteById(Long id);
}
