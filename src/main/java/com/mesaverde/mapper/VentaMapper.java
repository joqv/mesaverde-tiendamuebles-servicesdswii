package com.mesaverde.mapper;

import com.mesaverde.dto.response.VentaResponse;
import com.mesaverde.entity.DetalleVentaEntity;
import com.mesaverde.entity.VentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DetalleVentaMapper.class})
public interface VentaMapper {

    @Mapping(target = "detallesVentas", source = "detalles")
    @Mapping(target = "nombreUsuario", source = "entity.usuario.username")
    VentaResponse toVentaResponse(VentaEntity entity, List<DetalleVentaEntity> detalles);

    //List<VentaResponse> toVentaResponseList(List<VentaEntity> ventas);
}
