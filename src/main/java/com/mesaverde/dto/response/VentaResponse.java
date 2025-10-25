package com.mesaverde.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import com.mesaverde.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Builder
@Data
public class VentaResponse {

    //private String nombreCliente;
    private String nombreUsuario;
    private LocalDateTime fecha;
    private BigDecimal total;
    //private List<UserResponse> clientes;

    private List<DetalleVentaSimple> detallesVentas;

}


