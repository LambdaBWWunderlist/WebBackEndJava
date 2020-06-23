package com.e94.wunderlist.services;

import com.e94.wunderlist.exceptions.ResourceFoundException;
import com.e94.wunderlist.exceptions.ResourceNotFoundException;
import com.e94.wunderlist.models.Item;
import com.e94.wunderlist.models.Role;
import com.e94.wunderlist.models.User;
import com.e94.wunderlist.repositories.ItemRepository;
import com.e94.wunderlist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "itemService")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public Item addNewItem(String itemname) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(authentication.getName());
        Item newItem = new Item(itemname, currentUser);


        return itemRepository.save(newItem);
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();

        itemRepository.findAll().iterator().forEachRemaining(items::add);

        return items;
    }

    @Transactional
    @Override
    public Item update(Item newItem, long id) {
        Item currentItem = itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        currentItem.setItemname(newItem.getItemname());

        return itemRepository.save(currentItem);
    }

    @Override
    public List<Item> getItemsByUserId(long id) {
        User currentUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        return currentUser.getItems();
    }

    @Override
    public Item findItemById(long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
    }

    @Override
    public void delete(long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        itemRepository.delete(item);
    }
}
