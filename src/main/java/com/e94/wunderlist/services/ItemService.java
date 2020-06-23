package com.e94.wunderlist.services;

import com.e94.wunderlist.models.Item;

import java.util.List;

public interface ItemService {
    Item addNewItem(String itemname);

    List<Item> getAllItems();
}
