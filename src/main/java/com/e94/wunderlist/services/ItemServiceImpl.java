package com.e94.wunderlist.services;

import com.e94.wunderlist.exceptions.ResourceFoundException;
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

    @Autowired
    private UserAuditing userAuditing;

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
    public Item save(Item item) {
            return itemRepository.save(item);
    }
}
