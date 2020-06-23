package com.e94.wunderlist.repositories;

import com.e94.wunderlist.models.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
