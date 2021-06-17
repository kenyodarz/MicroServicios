package co.com.personalsoft.items.services;

import co.com.personalsoft.items.clients.ProductoClientRest;
import co.com.personalsoft.items.models.Item;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceFeign implements ItemService {

    private final ProductoClientRest clientRest;

    public ItemServiceFeign(ProductoClientRest clientRest) {
        this.clientRest = clientRest;
    }

    @Override
    public List<Item> findAll() {
        return clientRest.listar().stream().map(
                producto -> new Item(producto, 1)
        ).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        return new Item(clientRest.getOne(id), cantidad);
    }
}
