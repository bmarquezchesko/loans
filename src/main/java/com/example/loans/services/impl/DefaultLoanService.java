package com.example.loans.services.impl;

import com.example.loans.domain.Loan;
import com.example.loans.domain.User;
import com.example.loans.exceptions.PageNotFoundException;
import com.example.loans.repository.LoanRepository;
import com.example.loans.response.LoansResponse;
import com.example.loans.response.Paging;
import com.example.loans.services.LoanService;
import com.example.loans.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DefaultLoanService implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    UserService userService;

    public LoansResponse getAllLoans(Integer size, Integer page) {
        Pageable pageable = buildPageableWithPreviousIndexPage(page, size);

        Page<Loan> result = loanRepository.findAll(pageable);

        checkValidPage(page, result.getTotalPages(), result.getTotalElements());

        return buildLoansResponse(result, page, size);
    }

    public LoansResponse getAllLoansByUser(Long userId, Integer size, Integer page) {
        Pageable pageable = buildPageableWithPreviousIndexPage(page, size);

        User user = userService.getUser(userId);
        Page<Loan> result = loanRepository.findAllByUser(user, pageable);

        checkValidPage(page, result.getTotalPages(), result.getTotalElements());

        return buildLoansResponse(result, page, size);
    }

    private PageRequest buildPageableWithPreviousIndexPage(Integer page, Integer size) {
        return PageRequest.of(page, size).previous();
    }

    private void checkValidPage(int numberPage, int totalPages, long totalElements) {
        if (totalElements != 0 && numberPage > totalPages){
            throw new PageNotFoundException(String.format("Number page not exists, max page is %d", totalPages));
        }
    }

    private LoansResponse buildLoansResponse(Page<Loan> result, Integer page, Integer size) {
        Paging pagingInfo = new Paging(page, size, result.getTotalElements());

        return new LoansResponse(result.getContent(), pagingInfo);
    }
}
