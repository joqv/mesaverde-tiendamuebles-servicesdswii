package com.mesaverde.controller;

import com.mesaverde.dto.response.VentaResponse;
import com.mesaverde.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public List<VentaResponse> listaVentaDetalle() {
        return ventaService.obtenerTodasLasVentas();
    }
}