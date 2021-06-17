package co.com.personalsoft.items.services;

import co.com.personalsoft.items.models.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAll();
    Item findById(Long id, Integer cantidad);
}
