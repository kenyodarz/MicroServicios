package co.com.personalsoft.items.models;

import com.bykenyodarz.commons.models.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Producto producto;
    private Integer cantidad;

    public Double getTotal() {
        return producto.getPrecio() * cantidad.doubleValue();
    }
}
