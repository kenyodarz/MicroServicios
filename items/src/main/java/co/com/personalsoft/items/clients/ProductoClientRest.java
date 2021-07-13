package co.com.personalsoft.items.clients;

import com.bykenyodarz.commons.models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "servicio-productos")
public interface ProductoClientRest {
    @GetMapping("/all")
    List<Producto> listar();

    @GetMapping("/{id}")
    Producto getOne(@PathVariable Long id);

    @PostMapping("/save")
    Producto create(@RequestBody Producto producto);

    @GetMapping("/delete/{id}")
    void eliminar(@PathVariable Long id);
}
