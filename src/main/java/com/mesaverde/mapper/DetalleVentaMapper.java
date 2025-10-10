package com.mesaverde.mapper;

import com.mesaverde.dto.response.DetalleVentaSimple;
import com.mesaverde.entity.DetalleVentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetalleVentaMapper {

    @Mapping(target = "nombreProducto", source = "producto.nombre")
    DetalleVentaSimple toDetallVentaSimple(DetalleVentaEntity entity);
}
