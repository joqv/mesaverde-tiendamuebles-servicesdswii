package com.mesaverde.service;
import com.mesaverde.dto.response.VentaResponse;
import com.mesaverde.entity.DetalleVentaEntity;
import com.mesaverde.entity.VentaEntity;
import com.mesaverde.mapper.VentaMapper;
import com.mesaverde.repository.DetalleVentaRepository;
import com.mesaverde.repository.VentaRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    //private final VentaMapper ventaMapper;

    public List<VentaResponse> obtenerTodasLasVentas() {

        List<VentaEntity> ventasEntity = ventaRepository.findAll();

        List<DetalleVentaEntity> detalleVentasEntity = detalleVentaRepository.findByVentaIn(ventasEntity);

        Map<Long, List<DetalleVentaEntity>> detallesPorVentaId = detalleVentasEntity.stream()
                .collect(Collectors.groupingBy(detalle -> detalle.getVenta().getId()));

        List<VentaResponse> ventaResponse = new ArrayList<>();

        for (VentaEntity entity : ventasEntity) {
            List<DetalleVentaEntity> detalles = detallesPorVentaId.getOrDefault(
                    entity.getId(), Collections.emptyList()
            );

          //  VentaResponse response = ventaMapper.toVentaResponse(entity, detalles);

            ventaResponse.add(null);
        }

        return ventaResponse;
    }
}