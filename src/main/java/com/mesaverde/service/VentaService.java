package com.mesaverde.service;
import com.mesaverde.dto.response.UserResponse;
import com.mesaverde.dto.response.VentaResponse;
import com.mesaverde.entity.DetalleVentaEntity;
import com.mesaverde.entity.ProductoEntity;
import com.mesaverde.entity.VentaEntity;
import com.mesaverde.mapper.VentaMapper;
import com.mesaverde.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import com.mesaverde.util.BusinessException;

import com.mesaverde.repository.VentaRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final VentaMapper ventaMapper;

    public List<ProductoEntity> todosProductos(){
        return ventaRepository.todosProductos();
    }

    public VentaResponse obtenerVenta(long id) {

        VentaEntity venta = ventaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("venta.not.found"));

        List<UserResponse> clientes = userDetailsRepository.findAll().stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getRole(),
                        user.getCliente() != null ? user.getCliente().getId() : 1L
                ))
                .collect(Collectors.toList());

        System.out.println("clientes: " + clientes);


        VentaResponse response = VentaResponse.builder()
                    //.nombreCliente(venta.getCliente().getNombre())
                .nombreUsuario(venta.getUsuario() != null ? venta.getUsuario().getUsername() : "sistema")
                .fecha(venta.getFecha())
                    .total(venta.getTotal())
                    //.clientes(clientes)
                    .build();

        return response;
    }

    @Transactional
    public void procesarVenta(VentaEntity venta, List<DetalleVentaEntity> detalles) {
        // 1. Registrar la venta
        long ventaId = ventaRepository.registrarVenta(
                venta.getUsuario() != null ? venta.getUsuario().getId() : 1L,
                venta.getTotal()
        );
        venta.setId(ventaId);

        // 2. Procesar cada detalle
        for (DetalleVentaEntity detalle : detalles) {
            // 2.1 Descontar stock

            if (detalle.getProducto() == null || detalle.getProducto().getId() == null) {
                throw new BusinessException("El detalle de venta debe tener un producto con ID válido");
            }

            ventaRepository.descontarProducto(
                    detalle.getProducto().getId(),
                    detalle.getCantidad(),
                    detalle.getPrecioUnitario(),
                    venta.getUsuario() != null ? venta.getUsuario().getUsername() : "sistema"
            );

            // 2.2 Registrar detalle
            ProductoEntity prod = detalle.getProducto();

            ventaRepository.registrarDetalleVenta(
                    ventaId,
                    prod.getId(),
                    detalle.getCantidad(),
                    detalle.getPrecioUnitario()
            );
        }
    }

    public List<VentaResponse> obtenerTodasLasVentas() {

        List<VentaEntity> ventasEntity = ventaRepository.findAll();

        List<DetalleVentaEntity> detalleVentasEntity = detalleVentaRepository.findByVentaIn(ventasEntity);

        Map<Long, List<DetalleVentaEntity>> detallesPorVentaId = detalleVentasEntity.stream()
                .collect(Collectors.groupingBy(detalle -> detalle.getVenta().getId()));

        List<VentaResponse> ventaResponse = new ArrayList<>();

        for (VentaEntity entity : ventasEntity) {
            List<DetalleVentaEntity> detalles = detallesPorVentaId.getOrDefault(
                    entity.getId(), Collections.emptyList()
            );

            VentaResponse response = ventaMapper.toVentaResponse(entity, detalles);

            ventaResponse.add(response);
        }

        return ventaResponse;
    }

}
