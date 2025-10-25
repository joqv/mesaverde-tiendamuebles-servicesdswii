package com.mesaverde.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetalleVentaRequest {
    private int cantidad;
    private BigDecimal precioUnitario;
    private Long productoId;
    private Long ventaId;
}
