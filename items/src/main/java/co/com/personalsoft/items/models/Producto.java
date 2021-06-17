package co.com.personalsoft.items.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Producto {
    private Long idProducto;
    private String nombre;
    private Double precio;
    private LocalDate createdAt;
}
