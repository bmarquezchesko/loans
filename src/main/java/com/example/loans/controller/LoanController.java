package com.example.loans.controller;

import com.example.loans.response.LoansResponse;
import com.example.loans.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class LoanController {

    @Autowired
    LoanService loanService;

    @GetMapping
    public ResponseEntity<LoansResponse> getLoans(@RequestParam @Min(1) Integer size,
                                                 @RequestParam @Min(1) Integer page,
                                                 @RequestParam(name = "user_id", required = false) Optional<Long> optUserId){
        LoansResponse response = (optUserId.isPresent())
                ? loanService.getAllLoansByUser(optUserId.get(), size, page)
                : loanService.getAllLoans(size, page);

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
