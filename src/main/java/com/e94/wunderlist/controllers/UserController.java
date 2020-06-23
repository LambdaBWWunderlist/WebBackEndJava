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

    @PutMapping(value = "/user/{id}", consumes = {"application/json"})
    public ResponseEntity<?> updateFullUser(@PathVariable long id, @Valid @RequestBody User user){
        user.setUserid(id);
        userService.save(user);
        return new ResponseEntity<>(user.getUsername() + " was updated", HttpStatus.OK);
    }

    @PatchMapping(value = "/user", consumes = {"application/json"})
    public ResponseEntity<?> updatePartialUser(@Valid @RequestBody User user){
        userService.update(user);
        return new ResponseEntity<>(user.getUsername() + " has been updated", HttpStatus.OK);
    }

    @GetMapping(value = "/contains/{query}", produces = {"application/json"})
    public ResponseEntity<?> getByNameContaining(@PathVariable String query){
        List<User> rtnUserList = userService.findByNameContaining(query);
        return new ResponseEntity<>(rtnUserList, HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/{id}", produces = {"application/json"})
    public ResponseEntity<?> deleteUserById(@PathVariable long id){
        userService.delete(id);
        return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
    }
}
