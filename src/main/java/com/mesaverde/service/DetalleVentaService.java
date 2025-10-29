package com.mesaverde.service;

import com.mesaverde.entity.DetalleVentaEntity;
import com.mesaverde.repository.DetalleVentaRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;
}