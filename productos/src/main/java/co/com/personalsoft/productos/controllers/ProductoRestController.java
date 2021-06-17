package co.com.personalsoft.productos.controllers;

import co.com.personalsoft.productos.models.Producto;
import co.com.personalsoft.productos.services.apis.ProductoServiceAPI;
import co.com.personalsoft.productos.shared.GenericRestController;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductoRestController extends GenericRestController<Producto, Long> {
    public ProductoRestController(ProductoServiceAPI serviceAPI) {
        super(serviceAPI);
    }
}
