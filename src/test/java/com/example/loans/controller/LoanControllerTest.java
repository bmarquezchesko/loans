package com.example.loans.controller;

import com.example.loans.domain.Loan;
import com.example.loans.domain.User;
import com.example.loans.exceptions.PageNotFoundException;
import com.example.loans.response.LoansResponse;
import com.example.loans.response.Paging;
import com.example.loans.services.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = LoanController.class)
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetAllLoansSuccessfullyWithStatus200ReturnsTwoLoans() throws Exception {

        LoansResponse expectedLoans = buildLoansResponse();

        doReturn(expectedLoans).when(loanService).getAllLoans(anyInt(),anyInt());

        mockMvc.perform(get("/loans")
                .contentType("application/json")
                .param("size", "5")
                .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.items[0].id").value(expectedLoans.getItems().get(0).getId()))
                .andExpect(jsonPath("$.items[0].total").value(expectedLoans.getItems().get(0).getTotal()))
                .andExpect(jsonPath("$.items[1].id").value(expectedLoans.getItems().get(1).getId()))
                .andExpect(jsonPath("$.items[1].total").value(expectedLoans.getItems().get(1).getTotal()))
                .andExpect(jsonPath("$.paging.total").value(expectedLoans.getPaging().getTotal()))
        ;
    }

    @Test
    void testGetAllLoansByUserSuccessfullyWithStatus200ReturnsTwoLoans() throws Exception {

        LoansResponse expectedLoans = buildLoansResponse();

        doReturn(expectedLoans).when(loanService).getAllLoansByUser(anyLong(), anyInt(),anyInt());

        mockMvc.perform(get("/loans")
                .contentType("application/json")
                .param("size", "5")
                .param("page", "1")
                .param("user_id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.items[0].id").value(expectedLoans.getItems().get(0).getId()))
                .andExpect(jsonPath("$.items[0].total").value(expectedLoans.getItems().get(0).getTotal()))
                .andExpect(jsonPath("$.items[1].id").value(expectedLoans.getItems().get(1).getId()))
                .andExpect(jsonPath("$.items[1].total").value(expectedLoans.getItems().get(1).getTotal()))
                .andExpect(jsonPath("$.paging.total").value(expectedLoans.getPaging().getTotal()))
        ;
    }

    @Test
    void testGetLoansFailsWhenNotExistObligatoryPageParamThenReturns400() throws Exception {

        mockMvc.perform(get("/loans")
                .contentType("application/json")
                .param("size", "5"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Missing Parameter Exception"))
                .andExpect(jsonPath("$.message").value("Required Integer parameter 'page' is not present"))
                .andExpect(jsonPath("$.status").value("400"))
        ;
    }

    @Test
    void testGetLoansFailsWhenPageParamIsLessThanOneThenReturns422() throws Exception {

        mockMvc.perform(get("/loans")
                .contentType("application/json")
                .param("size", "5")
                .param("page", "0"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.error").value("Constraint Violation Exception"))
                .andExpect(jsonPath("$.message").value("getLoans.page: must be greater than or equal to 1"))
                .andExpect(jsonPath("$.status").value("422"))
        ;
    }

    @Test
    void testGetLoansFailsWhenPageNotFoundThenReturns404() throws Exception {

        PageNotFoundException expectedException = new PageNotFoundException("Number page not exists, max page is 1");

        doThrow(expectedException).when(loanService).getAllLoans(5, 3);

        mockMvc.perform(get("/loans")
                .contentType("application/json")
                .param("size", "5")
                .param("page", "3"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Page not found Exception"))
                .andExpect(jsonPath("$.message").value(expectedException.getMessage()))
                .andExpect(jsonPath("$.status").value("404"))
        ;
    }

    @Test
    void testGetLoansFailsWhenSizeParamIsStringThenReturns400() throws Exception {

        mockMvc.perform(get("/loans")
                .contentType("application/json")
                .param("size", "uno")
                .param("page", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Argument Type Mismatch Exception"))
                .andExpect(jsonPath("$.message").value("Failed to convert value of type 'java.lang.String' to " +
                        "required type 'java.lang.Integer'; nested exception is java.lang.NumberFormatException: For input string: \"uno\""))
                .andExpect(jsonPath("$.status").value("400"))
        ;
    }

    private LoansResponse buildLoansResponse() {
        List<Loan> loans = new ArrayList();
        loans.add(new Loan().setId(1L).setTotal(100L).setUser(new User().setId(1L)));
        loans.add(new Loan().setId(2L).setTotal(200L).setUser(new User().setId(1L)));

        return new LoansResponse()
                .setItems(loans)
                .setPaging(new Paging()
                        .setPage(1)
                        .setSize(5)
                        .setTotal(2L)
                );
    }
}