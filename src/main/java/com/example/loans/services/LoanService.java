package com.example.loans.services;

import com.example.loans.response.LoansResponse;

public interface LoanService {

    LoansResponse getAllLoans(Integer size, Integer page);

    LoansResponse getAllLoansByUser(Long userId, Integer size, Integer page);
}
