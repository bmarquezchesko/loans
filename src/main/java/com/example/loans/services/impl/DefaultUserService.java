package com.example.loans.services.impl;

import com.example.loans.domain.User;
import com.example.loans.exceptions.UserNotFoundException;
import com.example.loans.repository.UserRepository;
import com.example.loans.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("The user with ID %d does not exists", id)));
    }

    public User createUser(User user){

        //TODO: Validar si es un usuario ya existente en la BD y restringir la creación de LOANS
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
