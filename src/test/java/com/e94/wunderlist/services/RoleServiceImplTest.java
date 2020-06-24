package com.e94.wunderlist.services;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
class RoleServiceImplTest {
    @Autowired
    RoleService roleService;

    @Test
    @Order(1)
    void findAll() {
        assertEquals(2, roleService.findAll().size());
    }

    @Test
    @Order(2)
    void findRoleById() {
        assertEquals("ADMIN", roleService.findRoleById(1).getName());
    }

    @Test
    @Order(3)
    void findByName() {
        assertEquals("ADMIN", roleService.findByName("admin").getName());
    }

    @Test
    void save() {
    }

    @Test
    @Order(4)
    void delete() {
        roleService.delete(2);
        assertEquals(1, roleService.findAll().size());
    }

    @Test
    void update() {
    }
}