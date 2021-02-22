package com.example.loans.services;

import com.example.loans.domain.Loan;
import com.example.loans.domain.User;
import com.example.loans.exceptions.PageNotFoundException;
import com.example.loans.repository.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LoansService{

    @Autowired
    LoansRepository loansRepository;

    @Autowired
    UserService userService;

    public Page<Loan> findAll(Pageable pageable) {
        return loansRepository.findAll(pageable);
    }

    public Page<Loan> findAllByUser(Long userId, Pageable pageable) {

        User user = userService.getUser(userId);

        return loansRepository.findAllByUser(user, pageable);
    }
}
