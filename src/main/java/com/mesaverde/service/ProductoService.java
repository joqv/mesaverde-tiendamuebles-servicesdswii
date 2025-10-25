package com.mesaverde.service;

import com.mesaverde.entity.ProductoEntity;
import com.mesaverde.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;


    public List<ProductoEntity> listarTodos() {
        return productoRepository.findAll();
    }


    public Optional<ProductoEntity> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }


    public ProductoEntity registrar(ProductoEntity producto) {
        return productoRepository.save(producto);
    }


    public ProductoEntity actualizar(Long id, ProductoEntity producto) {
        return productoRepository.findById(id)
                .map(existing -> {
                    existing.setNombre(producto.getNombre());
                    existing.setPrecio(producto.getPrecio());
                    existing.setTipo(producto.getTipo());
                    existing.setStock(producto.getStock());
                    existing.setDescripcion(producto.getDescripcion());
                    existing.setImagen(producto.getImagen());
                    existing.setCategoria(producto.getCategoria());
                    return productoRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }


    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }


    public List<ProductoEntity> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
