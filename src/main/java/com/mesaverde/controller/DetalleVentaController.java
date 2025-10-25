package com.mesaverde.controller;

import com.mesaverde.dto.response.DetalleVentaRequest;
import com.mesaverde.dto.response.DetalleVentaSimple;
import com.mesaverde.service.DetalleVentaService;
import com.mesaverde.util.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/detalle-venta")
@RequiredArgsConstructor
public class DetalleVentaController {

    private final DetalleVentaService detalleVentaService;

    @GetMapping
    public List<DetalleVentaSimple> listarDetalles() {
        return detalleVentaService.obtenerTodos();
    }

    @GetMapping("/venta/{ventaId}")
    public List<DetalleVentaSimple> listarPorVenta(@PathVariable Long ventaId) {
        return detalleVentaService.obtenerPorVenta(ventaId);
    }

    @GetMapping("/{id}")
    public DetalleVentaSimple obtenerDetalle(@PathVariable Long id) {
        return detalleVentaService.obtenerPorId(id)
                .orElseThrow(() -> new BusinessException("Detalle no encontrado"));
    }

    @PutMapping("/{id}")
    public DetalleVentaSimple actualizar(@PathVariable Long id, @RequestBody DetalleVentaRequest request) {
        return detalleVentaService.actualizarDetalle(id, request);
    }

}