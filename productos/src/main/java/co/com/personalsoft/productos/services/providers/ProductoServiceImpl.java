package co.com.personalsoft.productos.services.providers;

import co.com.personalsoft.productos.models.Producto;
import co.com.personalsoft.productos.repositories.ProductoRepository;
import co.com.personalsoft.productos.services.apis.ProductoServiceAPI;
import co.com.personalsoft.productos.shared.GenericServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl extends GenericServiceImpl<Producto, Long> implements ProductoServiceAPI {

    private final ProductoRepository repository;

    public ProductoServiceImpl(ProductoRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<Producto, Long> getJpaRepository() {
        return this.repository;
    }
}
