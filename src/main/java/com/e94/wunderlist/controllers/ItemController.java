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
@RequestMapping("api/items")
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
}
