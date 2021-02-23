package com.example.loans.services;

import com.example.loans.domain.User;

public interface UserService {

    User getUser(Long id);

    User createUser(User user);

    void deleteUser(Long id);
}
