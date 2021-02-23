package com.example.loans.controller;

import com.example.loans.domain.User;
import com.example.loans.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        LOGGER.info(String.format("### GET request to obtain a user - Endpoint /users/%d was invoked ###", id));

        User response = userService.getUser(id);

        LOGGER.info(String.format("### Finish GET request to obtain a user successfully with response: %s ###", response));

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        LOGGER.info(String.format("### POST request to create a user - Endpoint /users was invoked with request body: %s ###", user));

        User response = userService.createUser(user);

        LOGGER.info(String.format("### Finish POST request to create a user successfully with response %s ###", response));

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id){
        LOGGER.info(String.format("### DELETE request to remove a user - Endpoint /users/%s was invoked ###", id));

        userService.deleteUser(id);

        LOGGER.info(String.format("### Finish DELETE request to remove user %d successfully ###", id));

        return ResponseEntity.ok("User deleted successfully!");
    }

}
