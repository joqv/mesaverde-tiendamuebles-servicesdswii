package com.mesaverde.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DetalleVentaSimple {

    private int id;
    private int cantidad;
    private String nombreProducto;
    private BigDecimal precioUnitario;
}
