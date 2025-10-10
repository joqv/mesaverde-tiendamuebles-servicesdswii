package com.mesaverde.service;

import com.mesaverde.entity.DetalleVentaEntity;
import com.mesaverde.repository.DetalleVentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;
}
