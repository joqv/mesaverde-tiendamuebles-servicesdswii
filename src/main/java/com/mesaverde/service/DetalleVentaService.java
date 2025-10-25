package com.mesaverde.service;

import com.mesaverde.dto.response.DetalleVentaRequest;
import com.mesaverde.dto.response.DetalleVentaSimple;
import com.mesaverde.entity.DetalleVentaEntity;
import com.mesaverde.entity.ProductoEntity;
import com.mesaverde.entity.VentaEntity;
import com.mesaverde.repository.DetalleVentaRepository;
import com.mesaverde.repository.ProductoRepository;
import com.mesaverde.repository.VentaRepository;
import com.mesaverde.util.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetalleVentaService {
    private final DetalleVentaRepository detalleVentaRepository;
    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;

    public List<DetalleVentaSimple> obtenerTodos() {
        return detalleVentaRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<DetalleVentaSimple> obtenerPorVenta(Long ventaId) {
        if (ventaId == null) {
            throw new BusinessException("El ID de la venta no puede ser nulo");
        }
        if (!ventaRepository.existsById(ventaId)) {
            throw new BusinessException("No existe una venta con el ID: " + ventaId);
        }
        List<DetalleVentaEntity> detalles = detalleVentaRepository.findByVentaId(ventaId);
        if (detalles.isEmpty()) {
            throw new BusinessException("No hay detalles registrados para la venta con ID: " + ventaId);
        }

        return detalles.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<DetalleVentaSimple> obtenerPorId(Long id) {
        return detalleVentaRepository.findById(id)
                .map(this::mapToDTO);
    }

    public DetalleVentaSimple actualizarDetalle(Long id, DetalleVentaRequest request) {
        DetalleVentaEntity entity = detalleVentaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Detalle de venta no encontrado"));

        entity.setCantidad(request.getCantidad());
        entity.setPrecioUnitario(request.getPrecioUnitario());

        if (request.getProductoId() != null) {
            ProductoEntity producto = productoRepository.findById(request.getProductoId())
                    .orElseThrow(() -> new BusinessException("Producto no encontrado"));
            entity.setProducto(producto);
        }

        if (request.getVentaId() != null) {
            VentaEntity venta = ventaRepository.findById(request.getVentaId())
                    .orElseThrow(() -> new BusinessException("Venta no encontrada"));
            entity.setVenta(venta);
        }

        return mapToDTO(detalleVentaRepository.save(entity));
    }

    private DetalleVentaSimple mapToDTO(DetalleVentaEntity entity) {
        BigDecimal subtotal = entity.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(entity.getCantidad()));

        return DetalleVentaSimple.builder()
                .id(entity.getId() != null ? entity.getId().intValue() : 0)
                .cantidad(entity.getCantidad())
                .nombreProducto(entity.getProducto().getNombre())
                .precioUnitario(entity.getPrecioUnitario())
                .subtotal(subtotal)
                .ventaId(entity.getVenta().getId())
                .build();
    }
}