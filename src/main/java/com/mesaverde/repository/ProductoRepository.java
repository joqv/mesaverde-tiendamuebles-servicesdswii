package com.mesaverde.repository;

import com.mesaverde.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity,Long> {
    // Buscar por nombre (contiene)
    List<ProductoEntity> findByNombreContainingIgnoreCase(String nombre);
}
