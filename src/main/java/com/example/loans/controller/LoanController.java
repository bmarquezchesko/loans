package com.example.loans.controller;

import com.example.loans.config.ControllerExceptionHandler;
import com.example.loans.response.LoansResponse;
import com.example.loans.services.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanController.class);

    @Autowired
    LoanService loanService;

    @GetMapping
    public ResponseEntity<LoansResponse> getLoans(@RequestParam @Min(1) Integer size,
                                                 @RequestParam @Min(1) Integer page,
                                                 @RequestParam(name = "user_id", required = false) Optional<Long> optUserId){
        LOGGER.info(String.format("### GET request to obtain allLoans/allLoansByUser - Endpoint /loans was invoked with Params: size= %d, page= %d, user_id= %d ###", size, page, optUserId.orElse(null)));

        LoansResponse response = (optUserId.isPresent())
                ? loanService.getAllLoansByUser(optUserId.get(), size, page)
                : loanService.getAllLoans(size, page);

        LOGGER.info(String.format("### Finish GET request to obtain allLoans/allLoansByUser successfully with response: %s ###", response));

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
