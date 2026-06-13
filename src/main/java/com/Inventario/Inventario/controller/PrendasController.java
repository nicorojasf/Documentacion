package com.Inventario.Inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Inventario.Inventario.model.Prendas;
import com.Inventario.Inventario.service.PrendasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequiredArgsConstructor
@RequestMapping("api/prendas")
@Tag(name = "Prendas", description = "Operaciones relacionadas con prendas en el inventario de la clinica ")
public class PrendasController {

    private final PrendasService prendasService;






    @GetMapping("/{id}")
    @Operation(summary = "Obtener prenda por ID", description = "Obtiene una prenda específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prenda encontrada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Prenda no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Prendas> obtenerPorId(@PathVariable("id") Integer id){
        return ResponseEntity.ok(prendasService.obtenerPorId(id));
    }
    





    @GetMapping("/listar")
    @Operation(summary = "Listar todas las prendas", description = "Obtiene una lista de todas las prendas en el inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prendas encontradas exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "No se encontraron prendas"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Prendas>> listarTodo() {
    return ResponseEntity.ok(prendasService.obtenerTodas());
    }






    @PostMapping("/guardar")
    @Operation(summary = "Guardar prenda", description = "Guarda una nueva prenda en el inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prenda guardada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "No se encontró el recurso relacionado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Prendas> guardar(@RequestBody Prendas prendas) {
    return ResponseEntity.ok(prendasService.guardar(prendas));
    }






    @PutMapping("/{id}/descontar")
    @Operation(summary = "Descontar stock", description = "Desconta cantidad especificada de stock de una prenda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock descontado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Prenda no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> descontar(@PathVariable Integer id, @RequestParam Integer cantidad) {
    prendasService.descontarStock(id, cantidad);
    return ResponseEntity.ok("Stock descontado exitosamente en Inventario");
    }






    @PutMapping("/{id}/aumentar")
    @Operation(summary = "Aumentar stock", description = "Aumenta cantidad especificada de stock de una prenda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock aumentado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Prenda no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<String> aumentar(@PathVariable Integer id, @RequestParam Integer cantidad) {
    prendasService.aumentarStock(id, cantidad);
    return ResponseEntity.ok("Stock aumentado exitosamente en Inventario");
}
}
