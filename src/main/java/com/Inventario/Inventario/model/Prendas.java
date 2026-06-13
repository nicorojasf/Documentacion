package com.Inventario.Inventario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Prendas")
@Data
public class Prendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_prenda", nullable = false)
    private String nombrePrenda;

    @Column(name = "material", nullable = false)
    private String material;

    @Column(name = "categoria", nullable = false)
    private String categoria;

    @Column(name = "STOCK_ACTUAL", nullable = false)
    private Integer stockActual;

    @Column(name = "stock_bodega", nullable = false)
    private Integer stockBodega;

    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo;
    

}
