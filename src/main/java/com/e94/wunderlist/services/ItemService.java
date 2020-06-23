package com.e94.wunderlist.services;

import com.e94.wunderlist.models.Item;

import java.util.List;

public interface ItemService {
    Item addNewItem(String itemname);

    List<Item> getAllItems();

    Item findItemById(long id);

    Item update(Item item, long id);

    void delete(long id);

    List<Item> getItemsByUserId(long id);
}
