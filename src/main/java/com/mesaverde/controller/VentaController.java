package com.mesaverde.controller;

import com.mesaverde.dto.response.VentaResponse;
import com.mesaverde.entity.DetalleVentaEntity;
import com.mesaverde.entity.ProductoEntity;
import com.mesaverde.entity.VentaEntity;
import com.mesaverde.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


import com.mesaverde.dto.request.VentaRequest;
import com.mesaverde.dto.ProductoDTO;
import com.mesaverde.dto.response.VentaResponse;
import com.mesaverde.service.VentaService;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;



@RestController
@RequestMapping(value = "/ventas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VentaController {

    private final VentaService ventaService;

    @GetMapping("/{id}")
    public VentaResponse obtenerVenta(@PathVariable Integer id) {

        return ventaService.obtenerVenta(id);
    }

    //@GetMapping("/catalogo")
    //public List<Producto> todosProductos(){
    //	return ventaService.todosProductos();
    //}

    @GetMapping("/catalogo")
    public List<ProductoDTO> todosCatalogo() {
        return ventaService.todosProductos().stream()
                .map(ProductoDTO::new)
                .collect(Collectors.toList());
    }



    @PostMapping("/vender")
    public void procesarVenta(@RequestBody VentaRequest ventaRequest) {
        VentaEntity venta = new VentaEntity();
        //Fecha
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime fecha = LocalDateTime.parse(ventaRequest.getFecha(), formatter);
        venta.setFecha(fecha);
        //total
        BigDecimal total = BigDecimal.valueOf(ventaRequest.getTotal());
        venta.setTotal(total);

        List<DetalleVentaEntity> detalles = ventaRequest.getProductos().stream().map(p -> {
            DetalleVentaEntity detalle = new DetalleVentaEntity();

            ProductoEntity producto = new ProductoEntity();
            producto.setId(p.getId());
            producto.setNombre(p.getNombre()); // si existe
            producto.setPrecio(BigDecimal.valueOf(p.getPrecio_unitario())); // si existe

            detalle.setProducto(producto);
            detalle.setCantidad(p.getCantidad());
            detalle.setPrecioUnitario(BigDecimal.valueOf(p.getPrecio_unitario()));
            return detalle;
        }).collect(Collectors.toList());

        ventaService.procesarVenta(venta, detalles);
    }
}


