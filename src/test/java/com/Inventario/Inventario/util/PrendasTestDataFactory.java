package com.Inventario.Inventario.util;

import java.util.Locale;

import com.Inventario.Inventario.model.Prendas;

import net.datafaker.Faker;

public class PrendasTestDataFactory {
   
    private static final Faker faker = new Faker(new Locale("es"));

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

    public static Prendas crearPrendaAleatoria() {
        Prendas prenda = new Prendas();
        prenda.setId(faker.number().randomDigitNotZero());
        prenda.setNombrePrenda(faker.commerce().productName());
        prenda.setMaterial(faker.commerce().material());
        prenda.setCategoria(faker.options().option("Ropa de paciente", "Uniforme Médico", "Sábanas"));
        prenda.setStockActual(faker.number().numberBetween(10, 50));
        prenda.setStockBodega(faker.number().numberBetween(5, 20));
        prenda.setStockMinimo(faker.number().numberBetween(2, 5));
        return prenda;
    }
}
