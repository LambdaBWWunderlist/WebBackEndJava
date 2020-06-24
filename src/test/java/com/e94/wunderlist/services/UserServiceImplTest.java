package com.e94.wunderlist.services;

import com.e94.wunderlist.models.User;
import com.e94.wunderlist.models.UserRoles;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Order(1)
    void findUserById() {
        assertEquals("admin", userService.findUserById(3).getUsername());
    }

    @Test
    @Order(2)
    void findByNameContaining() {
        assertEquals(2, userService.findByNameContaining("admin").size());
    }

    @Test
    @Order(3)
    void findAll() {
        assertEquals(10, userService.findAll().size());
    }

    @Test
    @Order(4)
    void delete() {
        userService.delete(5);
        assertEquals(9, userService.findAll().size());
    }

    @Test
    @Order(5)
    void findByName() {
        assertEquals("admin", userService.findByName("admin").getUsername());
    }

    @Test
    @Order(6)
    void save() {
        List<UserRoles> roles = new ArrayList<>();
        User user = new User("username", "password", "email.email@mail.com", roles);

        userService.save(user);
        assertEquals(10, userService.findAll().size());
    }

    @Test
    @Order(7)
    void update() {
    }

    @Test
    void deleteUserRole() {
    }

    @Test
    void addUserRole() {
    }
}