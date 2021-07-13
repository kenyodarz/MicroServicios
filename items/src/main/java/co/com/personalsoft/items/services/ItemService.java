package co.com.personalsoft.items.services;

import co.com.personalsoft.items.models.Item;
import com.bykenyodarz.commons.models.Producto;

import java.util.List;

public interface ItemService {
    List<Item> findAll();

    Item findById(Long id, Integer cantidad);

    Producto save(Producto producto);

    void delete(Long id);
}
