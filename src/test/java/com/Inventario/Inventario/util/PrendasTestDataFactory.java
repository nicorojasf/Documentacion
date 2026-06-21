package com.Inventario.Inventario.util;


import com.Inventario.Inventario.model.Prendas;


public class PrendasTestDataFactory {
   
   public static Prendas crearPrendaValida() {
        Prendas prenda = new Prendas();
        prenda.setId(1);
        prenda.setNombrePrenda("Kimono");
        prenda.setMaterial("Algodón");
        prenda.setCategoria("Ropa de paciente");
        prenda.setStockActual(10);
        prenda.setStockBodega(5);
        prenda.setStockMinimo(2);
        return prenda;
    }

  
}
