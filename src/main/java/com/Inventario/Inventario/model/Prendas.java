package com.Inventario.Inventario.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entidad que representa una prenda en el inventario de la clínica")
public class Prendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Schema(description = "ID único de la prenda", example = "1")
    private Integer id;

    @Column(name = "nombre_prenda", nullable = false)
    @Schema(description = "Nombre de la prenda", example = "Kimono")
    private String nombrePrenda;

    @Column(name = "material", nullable = false)
    @Schema(description = "Material de la prenda", example = "Algodón")
    private String material;

    @Column(name = "categoria", nullable = false)
    @Schema(description = "Categoría de la prenda", example = "Ropa de paciente")
    private String categoria;

    @Column(name = "STOCK_ACTUAL", nullable = false)
    @Schema(description = "Stock actual de la prenda", example = "10")
    private Integer stockActual;

    @Column(name = "stock_bodega", nullable = false)
    @Schema(description = "Stock en bodega de la prenda", example = "5")
    private Integer stockBodega;

    @Column(name = "stock_minimo", nullable = false)
    @Schema(description = "Stock mínimo de la prenda", example = "2")
    private Integer stockMinimo;
    

}
