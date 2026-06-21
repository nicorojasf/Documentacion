package com.Inventario.Inventario.v2;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.Inventario.Inventario.model.Prendas;

@Component
public class PrendasModelAssemblerV2 extends RepresentationModelAssemblerSupport<Prendas, PrendasModelV2> {
    
    public PrendasModelAssemblerV2() {
        super(PrendasControllerV2.class, PrendasModelV2.class);
    }

    @Override
    public PrendasModelV2 toModel(Prendas entidad) {
        PrendasModelV2 model = instantiateModel(entidad);
        
        // Mapeo de datos
        model.setId(entidad.getId());
        model.setNombrePrenda(entidad.getNombrePrenda());
        model.setMaterial(entidad.getMaterial());
        model.setCategoria(entidad.getCategoria());
        model.setStockActual(entidad.getStockActual());
        model.setStockBodega(entidad.getStockBodega());
        model.setStockMinimo(entidad.getStockMinimo());

        // HATEOAS: Link Self
        model.add(linkTo(methodOn(PrendasControllerV2.class).obtenerPorId(entidad.getId())).withSelfRel());
        
        // HATEOAS: Links transaccionales específicos para transiciones de estado (Stock)
        model.add(linkTo(methodOn(PrendasControllerV2.class).descontar(entidad.getId(), null)).withRel("descontar_stock"));
        model.add(linkTo(methodOn(PrendasControllerV2.class).aumentar(entidad.getId(), null)).withRel("aumentar_stock"));

        return model;
    }
}
