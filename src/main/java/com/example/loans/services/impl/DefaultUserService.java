package com.example.loans.services.impl;

import com.example.loans.domain.User;
import com.example.loans.exceptions.LoansCreationForbiddenException;
import com.example.loans.exceptions.UserNotFoundException;
import com.example.loans.repository.UserRepository;
import com.example.loans.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("The user with ID %d does not exists", id)));
    }

    public User createUser(User user){
        if (!user.getLoans().isEmpty()){
            throw new LoansCreationForbiddenException("Loans creation is forbidden, only pre-existing users have loans");
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
