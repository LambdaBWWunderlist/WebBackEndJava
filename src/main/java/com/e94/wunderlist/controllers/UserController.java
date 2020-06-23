package com.e94.wunderlist.controllers;

import com.e94.wunderlist.models.User;
import com.e94.wunderlist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users", produces = {"application/json"})
    public ResponseEntity<?> getAllUsers(){
        List<User> userList = userService.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping(value = "/users", consumes = {"application/json"})
    public ResponseEntity<?> postUser(@Valid @RequestBody User newUser){
        newUser.setUserid(0);
        newUser = userService.save(newUser);

        return new ResponseEntity<>("created", HttpStatus.CREATED);
    }

    @GetMapping(value = "/user/{id}", produces = {"application/json"})
    public ResponseEntity<?> getUserById(@PathVariable long id){
        User rtnUser = userService.findUserById(id);
        return new ResponseEntity<>(rtnUser, HttpStatus.OK);
    }
}
