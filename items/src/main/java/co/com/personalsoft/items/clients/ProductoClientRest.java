package co.com.personalsoft.items.clients;

import co.com.personalsoft.items.models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "servicio-productos")
public interface ProductoClientRest {
    @GetMapping("/all")
    List<Producto> listar();
    @GetMapping("/{id}")
    Producto getOne(@PathVariable Long id);
}
