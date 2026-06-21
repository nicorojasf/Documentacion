package com.Inventario.Inventario.v2;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Representación optimizada (V2) de una prenda con hipermedios para mejorar la eficiencia en la transferencia de datos")
public class PrendasModelV2 extends RepresentationModel<PrendasModelV2> {

    @Schema(description = "ID único de la prenda", example = "1")
    private Integer id;

    @Schema(description = "Nombre de la prenda", example = "Kimono")
    private String nombrePrenda;

    @Schema(description = "Material de la prenda", example = "Algodón")
    private String material;

    @Schema(description = "Categoría de la prenda", example = "Ropa de paciente")
    private String categoria;

    @Schema(description = "Stock actual de la prenda", example = "10")
    private Integer stockActual;

    @Schema(description = "Stock en bodega de la prenda", example = "5")
    private Integer stockBodega;

    @Schema(description = "Stock mínimo de la prenda", example = "2")
    private Integer stockMinimo;
    
}
