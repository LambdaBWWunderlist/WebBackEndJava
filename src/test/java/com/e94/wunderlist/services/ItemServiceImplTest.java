package com.e94.wunderlist.services;

import com.e94.wunderlist.models.Item;
import com.e94.wunderlist.models.User;
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
class ItemServiceImplTest {

    @Autowired
    ItemService itemService;

    @Test
    @Order(1)
    void addNewItem() {
        assertEquals(10, itemService.getAllItems().size());
    }

    @Test
    @Order(2)
    void getAllItems() {
        assertEquals(10, itemService.getAllItems().size());
    }

    @Test
    void update() {
    }

    @Test
    void getItemsByUserId() {
    }

    @Test
    @Order(3)
    void findItemById() {
        assertEquals("Take out the trash", itemService.findItemById(4).getItemname());
    }

    @Test
    @Order(4)
    void delete() {
        itemService.delete(4);
        assertEquals(9, itemService.getAllItems().size());
    }
}