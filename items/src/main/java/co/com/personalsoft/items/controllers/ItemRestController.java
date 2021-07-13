package co.com.personalsoft.items.controllers;

import co.com.personalsoft.items.models.Item;
import co.com.personalsoft.items.services.ItemService;
import com.bykenyodarz.commons.models.Producto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RefreshScope
@EnableAutoConfiguration
@RestController
public class ItemRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemRestController.class);

    private final ItemService itemService;

    private final Environment env;

    @Value("${configuracion.texto}")
    private String text;


    @GetMapping("/all")
    public List<Item> listar() {
        return itemService.findAll();
    }

    @GetMapping("/{id}/{cantidad}")
    @CircuitBreaker(name = "PRODUCTOS", fallbackMethod = "metodoAlternativo")
    public Item getItem(@PathVariable Long id, @PathVariable Integer cantidad) {
        return itemService.findById(id, cantidad);
    }

    public Item metodoAlternativo(Long id, Integer cantidad, Exception ex) {
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

    @GetMapping("/obtener-config")
    public ResponseEntity<?> getConfig(@Value("${server.port}") String puerto) {
        LOGGER.info("Configuración -> {}", text);
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("text", text);
        jsonResponse.put("puerto", puerto);
        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            jsonResponse.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
            jsonResponse.put("autor.email", env.getProperty("configuracion.autor.email"));
        }
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> crear(@RequestBody Producto producto) {
        var productoResponse = itemService.save(producto);
        return ResponseEntity.created(URI
                .create("/".concat(productoResponse.getIdProducto().toString())))
                .body(producto);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        itemService.delete(id);
    }
}
