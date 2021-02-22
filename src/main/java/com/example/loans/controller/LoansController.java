package com.example.loans.controller;

import com.example.loans.domain.Loan;
import com.example.loans.exceptions.PageNotFoundException;
import com.example.loans.response.LoansResponse;
import com.example.loans.response.Paging;
import com.example.loans.services.LoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@RequestMapping("/loans")
@Validated
public class LoansController {

    @Autowired
    LoansService loansService;

    @GetMapping
    public LoansResponse getLoans(@RequestParam @Min(1) Integer size,
                                  @RequestParam @Min(1) Integer page,
                                  @RequestParam (required = false) Optional<Long> optionalUserId){

        Pageable pageable = PageRequest.of(page, size).previous();
        Page<Loan> result = (optionalUserId.isPresent())
                ? loansService.findAll(pageable)
                : loansService.findAllByUser(optionalUserId.get(), pageable);

        if (pageable.getPageNumber() >= result.getTotalPages()){
            throw new PageNotFoundException(String.format("Number page not exists, max page is %d", result.getTotalPages()));
        }

        Paging pagingInfo = new Paging(Long.valueOf(pageable.getPageNumber() + 1), Long.valueOf(pageable.getPageSize()), result.getTotalElements());
        LoansResponse response = new LoansResponse(result.getContent(), pagingInfo);

        return response;
    }
}
