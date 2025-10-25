package com.mesaverde.repository;

import com.mesaverde.entity.DetalleVentaEntity;
import com.mesaverde.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVentaEntity, Long> {

    List<DetalleVentaEntity> findByVentaIn(List<VentaEntity> ventas);

    List<DetalleVentaEntity> findByVentaId(Long ventaId);
}
