package com.mesaverde.service;
import com.mesaverde.dto.response.VentaResponse;
import com.mesaverde.entity.DetalleVentaEntity;
import com.mesaverde.entity.ProductoEntity;
import com.mesaverde.entity.VentaEntity;
import com.mesaverde.mapper.VentaMapper;
import com.mesaverde.repository.DetalleVentaRepository;
import com.mesaverde.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.error.springerrorhandler.exceptions.BusinessException;
import com.mesaverde.client.ClienteClient;
import com.mesaverde.dto.response.VentaResponse;
import com.mesaverde.entity.DetalleVenta;
import com.mesaverde.entity.Producto;
import com.mesaverde.entity.Venta;
import com.mesaverde.repository.VentaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteClient clienteClient;

    public List<ProductoEntity> todosProductos(){
        return ventaRepository.todosProductos();
    }

    public VentaResponse obtenerVenta(Integer id) {

        VentaEntity venta = ventaRepository.findById(id).orElseThrow(() ->
                new BusinessException("venta.not.found"));

        VentaResponse response = VentaResponse.builder()
                //.nombreCliente(venta.getCliente().getNombre())
                .nombreUsuario(venta.getUsuario().getNombre())
                .fecha(venta.getFecha())
                .total(venta.getTotal())
                .clientes(clienteClient.getClientes())
                .build();

        return response;
    }

    @Transactional
    public void procesarVenta(Venta venta, List<DetalleVenta> detalles) {
        // 1. Registrar la venta
        Integer ventaId = ventaRepository.registrarVenta(
                venta.getUsuario() != null ? venta.getUsuario().getId() : null,
                venta.getTotal()
        );
        venta.setId(ventaId);

        // 2. Procesar cada detalle
        for (DetalleVenta detalle : detalles) {
            // 2.1 Descontar stock
            ventaRepository.descontarProducto(
                    detalle.getProductoId(),
                    detalle.getCantidad(),
                    detalle.getPrecioUnitario(),
                    venta.getUsuario() != null ? venta.getUsuario().getNombre() : "sistema"
            );

            // 2.2 Registrar detalle
            ventaRepository.registrarDetalleVenta(
                    ventaId,
                    detalle.getProductoId(),
                    detalle.getCantidad(),
                    detalle.getPrecioUnitario()
            );
        }
    }

}




/*public class VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final VentaMapper ventaMapper;

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

 */