package com.example.loans.controller;

import com.example.loans.domain.Loan;
import com.example.loans.domain.User;
import com.example.loans.exceptions.LoansCreationForbiddenException;
import com.example.loans.exceptions.UserNotFoundException;
import com.example.loans.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetUserByIdSuccessfullyWithStatus200ReturnsUserWithoutLoans() throws Exception {

        User expectedUser = buildExpectedUser();

        doReturn(expectedUser).when(userService).getUser(anyLong());

        mockMvc.perform(get("/users/{id}", 1L)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedUser.getId()))
                .andExpect(jsonPath("$.firstName").value(expectedUser.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expectedUser.getLastName()))
                .andExpect(jsonPath("$.email").value(expectedUser.getEmail()))
                .andExpect(jsonPath("$.loans").isArray())
                .andExpect(jsonPath("$.loans", hasSize(0)))
        ;
    }

    @Test
    void testGetUserByIdSuccessfullyWithStatus200ReturnsUserWithTwoLoans() throws Exception {

        List<Loan> loans = new ArrayList<>();
        loans.add(new Loan(1L, 1000L, new User().setId(1L)));
        loans.add(new Loan(2L, 2000L, new User().setId(1L)));

        User expectedUser = buildExpectedUser().setLoans(loans);

        doReturn(expectedUser).when(userService).getUser(anyLong());

        mockMvc.perform(get("/users/{id}", 1L)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedUser.getId()))
                .andExpect(jsonPath("$.firstName").value(expectedUser.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expectedUser.getLastName()))
                .andExpect(jsonPath("$.email").value(expectedUser.getEmail()))
                .andExpect(jsonPath("$.loans").isArray())
                .andExpect(jsonPath("$.loans", hasSize(2)))
                .andExpect(jsonPath("$.loans[0].id").value(expectedUser.getLoans().get(0).getId()))
                .andExpect(jsonPath("$.loans[0].total").value(expectedUser.getLoans().get(0).getTotal()))
                .andExpect(jsonPath("$.loans[0].userId").value(expectedUser.getLoans().get(0).getUser().getId()))
                .andExpect(jsonPath("$.loans[1].id").value(expectedUser.getLoans().get(1).getId()))
                .andExpect(jsonPath("$.loans[1].total").value(expectedUser.getLoans().get(1).getTotal()))
                .andExpect(jsonPath("$.loans[1].userId").value(expectedUser.getLoans().get(1).getUser().getId()))
        ;
    }

    @Test
    void testGetUserByIdFailWithStatus404ReturnsUserNotFoundException() throws Exception {

        UserNotFoundException expectedException = new UserNotFoundException("The user with ID 1 does not exists");

        doThrow(expectedException).when(userService).getUser(1L);

        mockMvc.perform(get("/users/{id}", 1L)
                .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("User not found Exception"))
                .andExpect(jsonPath("$.message").value(expectedException.getMessage()))
                .andExpect(jsonPath("$.status").value("404"))
        ;
    }

    @Test
    void testCreateUserSuccessfullyWithStatus200ReturnsUserWithoutLoans() throws Exception {

        User expectedUser = buildExpectedUser();

        doReturn(expectedUser).when(userService).createUser(any(User.class));

        mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(expectedUser))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedUser.getId()))
                .andExpect(jsonPath("$.firstName").value(expectedUser.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(expectedUser.getLastName()))
                .andExpect(jsonPath("$.email").value(expectedUser.getEmail()))
                .andExpect(jsonPath("$.loans").isArray())
                .andExpect(jsonPath("$.loans").isEmpty())
        ;
    }

    @Test
    void testCreateUserFailsWithStatus403ReturnsForbiddenLoansCreationException() throws Exception {

        User userWithLoans = buildExpectedUser().setLoans(Arrays.asList(new Loan().setId(1L).setTotal(1000L)));
        LoansCreationForbiddenException ex = new LoansCreationForbiddenException("Loans creation is forbidden, only pre-existing users have loans");

        doThrow(ex).when(userService).createUser(any(User.class));

        mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(userWithLoans))
                .contentType("application/json"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value("Loans Creation Forbidden Exception"))
                .andExpect(jsonPath("$.message").value(ex.getMessage()))
                .andExpect(jsonPath("$.status").value("403"))
        ;
    }

    @Test
    void testCreateUserFailsWithStatus400ReturnsMethodArgumentNotValidException() throws Exception {

        User userWithoutFirstName = new User().setLastName("Amaya").setEmail("amaya@gmail.com");


        mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(userWithoutFirstName))
                .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Method Argument Not Valid"))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0]").value("Please provide a firstName attribute in JSON request"))

        ;
    }

    @Test
    void testDeleteUserByIdSuccessfullyWithStatus200() throws Exception {

        doNothing().when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/users/{id}", 1L)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully!"))
        ;
    }

    @Test
    void testDeleteUserByIdFailsWithStatus404ByEmptyResultDataException() throws Exception {

        EmptyResultDataAccessException ex = new EmptyResultDataAccessException("No class com.example.loans.domain.User entity with id 8 exists!", 1);
        doThrow(ex).when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/users/{id}", 1L)
                .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Empty Result Data Exception"))
                .andExpect(jsonPath("$.message").value(ex.getMessage()))
                .andExpect(jsonPath("$.status").value("404"))
        ;
    }

    private User buildExpectedUser(){
        return new User()
                .setId(1L)
                .setFirstName("Diego")
                .setLastName("Rodriguez")
                .setEmail("diegorodriguez@gmail.com");
    };
}