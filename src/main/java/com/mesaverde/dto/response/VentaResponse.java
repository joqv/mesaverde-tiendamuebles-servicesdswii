package com.mesaverde.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class VentaResponse {

    private int id;
    private LocalDateTime fecha;
    private List<DetalleVentaSimple> detallesVentas;
}
