package com.bykenyodarz.usuario.repositories;

import com.bykenyodarz.usuario.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByName(String name);

    Boolean existsByName(String name);
}
