package com.e94.wunderlist.controllers;

import com.e94.wunderlist.models.Item;
import com.e94.wunderlist.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping(value = "/item", consumes = {"application/json"})
    public ResponseEntity<?> postNewItem(@Valid @RequestBody String itemname){
        itemService.addNewItem(itemname);
        return new ResponseEntity<>("created item: " + itemname, HttpStatus.CREATED);
    }

    @GetMapping(value = "/items", produces = {"application/json"})
    public ResponseEntity<?> getAllItems(){
        List<Item> rtnLst = itemService.getAllItems();
        return new ResponseEntity<>(rtnLst, HttpStatus.OK);
    }

    @GetMapping(value = "/items/{userid}", produces = {"application/json"})
    public ResponseEntity<?> getItemsByUserId(@PathVariable long userid){
        List<Item> items = itemService.getItemsByUserId(userid);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PutMapping(value = "item/{id}", consumes = {"application/json"})
    public ResponseEntity<?> updateItem(@PathVariable long id, @Valid @RequestBody Item newItem){
        itemService.update(newItem, id);
        return new ResponseEntity<>("Item has been updated", HttpStatus.OK);
    }

    @DeleteMapping(value = "item/{id}", produces = {"application/json"})
    public ResponseEntity<?> deleteItemById(@PathVariable long id){
        itemService.delete(id);
        return new ResponseEntity<>("Item has been deleted", HttpStatus.OK);
    }

    @GetMapping(value = "/item/{id}", produces = {"application/json"})
    public ResponseEntity<?> findItemById(@PathVariable long id){
        Item item = itemService.findItemById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
