package co.com.personalsoft.productos.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    @Column
    private String nombre;
    @Column
    private Double precio;
    @Column(name = "created_at")
    private LocalDate createdAt;

    @PrePersist
    public void PrePersist(){
        createdAt = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Producto producto = (Producto) o;

        return Objects.equals(idProducto, producto.idProducto);
    }

    @Override
    public int hashCode() {
        return 1778999073;
    }

}
