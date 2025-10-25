package com.mesaverde.controller;

import com.mesaverde.entity.ProductoEntity;
import com.mesaverde.service.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@AllArgsConstructor
public class ProductoController {

    @Autowired
    private ProductoService productoService;


    @GetMapping
    public ResponseEntity<List<ProductoEntity>> listarTodos() {
        return ResponseEntity.ok(productoService.listarTodos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductoEntity> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<ProductoEntity> registrar(@RequestBody ProductoEntity producto) {
        ProductoEntity nuevo = productoService.registrar(producto);
        return ResponseEntity.ok(nuevo);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductoEntity> actualizar(@PathVariable Long id, @RequestBody ProductoEntity producto) {
        try {
            ProductoEntity actualizado = productoService.actualizar(id, producto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<ProductoEntity>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }
}
