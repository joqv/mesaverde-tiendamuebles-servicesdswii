package com.mesaverde.dto;

import java.math.BigDecimal;

import com.mesaverde.entity.ProductoEntity;

import com.mesaverde.entity.ProductoEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {
    private long id;
    private String nombre;
    private BigDecimal precio;
    private String tipo;
    private int stock;
    private String descripcion;
    private String imagen;
    private String categoriaNombre;

    public ProductoDTO(ProductoEntity producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.precio = producto.getPrecio();
        this.tipo = producto.getTipo();
        this.stock = producto.getStock();
        this.descripcion = producto.getDescripcion();
        this.imagen = producto.getImagen();
        this.categoriaNombre = producto.getCategoria() != null ? producto.getCategoria().getNombre() : null;
    }

    public ProductoDTO() {
    }

}


