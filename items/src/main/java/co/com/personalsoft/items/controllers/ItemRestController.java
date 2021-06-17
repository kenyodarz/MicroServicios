package co.com.personalsoft.items.controllers;

import co.com.personalsoft.items.models.Item;
import co.com.personalsoft.items.models.Producto;
import co.com.personalsoft.items.services.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemRestController.class);

    private final ItemService itemService;

    public ItemRestController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all")
    public List<Item> listar(){
        return itemService.findAll();
    }

    @GetMapping("/{id}/{cantidad}")
    @CircuitBreaker(name = "PRODUCTOS", fallbackMethod = "metodoAlternativo")
    public Item getItem(@PathVariable Long id, @PathVariable Integer cantidad){
        return itemService.findById(id, cantidad);
    }

    public Item metodoAlternativo(Long id, Integer cantidad, Exception ex){
        var item = new Item();
        var product = new Producto();

        item.setCantidad(cantidad);

        product.setIdProducto(id);
        product.setNombre("Producto Prueba");
        product.setPrecio(500.00);

        item.setProducto(product);

        LOGGER.info("Response 200, fallback method for error: {}", ex.getMessage());

        return item;
    }
}
