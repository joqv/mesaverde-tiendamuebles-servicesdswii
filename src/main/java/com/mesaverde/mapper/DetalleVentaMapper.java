package com.mesaverde.mapper;

import com.mesaverde.dto.response.DetalleVentaSimple;
import com.mesaverde.entity.DetalleVentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetalleVentaMapper {

    @Mapping(target = "nombreProducto", source = "producto.nombre")
    @Mapping(target = "ventaId", source = "venta.id")
    DetalleVentaSimple toDetallVentaSimple(DetalleVentaEntity entity);
}
