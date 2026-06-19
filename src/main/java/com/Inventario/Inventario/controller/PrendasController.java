package com.Inventario.Inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Inventario.Inventario.model.Prendas;
import com.Inventario.Inventario.service.PrendasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
    @ApiResponse(responseCode = "200", description = "Prenda encontrada exitosamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Prendas.class))),

    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"ID debe ser un número valido\"}"))),

    @ApiResponse(responseCode = "404", description = "Prenda no encontrada", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"Prenda con ID 47 no encontrada\"}"))),

    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"Ocurrió un error interno del servidor\"}")))
    })
    public ResponseEntity<Prendas> obtenerPorId(@Parameter(description = "ID de la prenda a obtener", example = "1")
                                                @PathVariable("id") Integer id){
        return ResponseEntity.ok(prendasService.obtenerPorId(id));
    }
    





    @GetMapping("/listar")
    @Operation(summary = "Listar todas las prendas", description = "Obtiene una lista de todas las prendas en el inventario")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Prendas encontradas exitosamente",
        content = @Content(mediaType = "application/json", 
        array = @ArraySchema(schema = @Schema(implementation = Prendas.class)))), 
    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"No se encontraron prendas\"}"))),

    @ApiResponse(responseCode = "404", description = "No se encontraron prendas", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"No se encontraron prendas registradas\"}"))),

    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"Ocurrió un error interno del servidor\"}")))
    })
    public ResponseEntity<List<Prendas>> listarTodo() {
    return ResponseEntity.ok(prendasService.obtenerTodas());
    }



    @PostMapping("/guardar")
    @Operation(summary = "Guardar prenda", description = "Guarda una nueva prenda en el inventario",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Dentro de un JSON colocas los datos de la prenda que se va a crear. No debes incluir el 'id' ya que se genera automáticamente.",
        required = true))
    @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Prenda guardada exitosamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Prendas.class))),

    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"No se encontro el recurso relacionado\"}"))),

    @ApiResponse(responseCode = "404", description = "No se encontró el recurso relacionado", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"La categoria especificada no existe\"}"))),

    @ApiResponse(responseCode = "409", description = "Conflicto en la solicitud", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"Ya existe una prenda con ese nombre\"}"))),

    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"Ocurrió un error interno del servidor\"}")))
    })
    public ResponseEntity<Prendas> guardar(@RequestBody Prendas prendas) {
    return ResponseEntity.status(201).body(prendasService.guardar(prendas));
    }






    @PutMapping("/{id}/descontar")
    @Operation(summary = "Descontar stock", description = "Descuenta cantidad especificada de stock de una prenda")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Stock descontado exitosamente",
        content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))), 

    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"Cantidad debe ser mayor a 0\"}"))),

    @ApiResponse(responseCode = "404", description = "Prenda no encontrada", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"Prenda con ID 47 no encontrada\"}"))),

    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"Ocurrió un error interno del servidor\"}")))
    })
    public ResponseEntity<String> descontar(
        @Parameter(description = "ID de la prenda a descontar", example = "1")
        @PathVariable Integer id,
        @Parameter(description = "Cantidad a descontar del stock", example = "2") 
        @RequestParam Integer cantidad)
         {
    prendasService.descontarStock(id, cantidad);
    return ResponseEntity.ok("Stock descontado exitosamente en Inventario");
    }






    @PutMapping("/{id}/aumentar")
    @Operation(summary = "Aumentar stock", description = "Aumenta cantidad especificada de stock de una prenda")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Stock aumentado exitosamente",
        content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))), 
    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"Cantidad debe ser mayor a 0\"}"))),

    @ApiResponse(responseCode = "404", description = "Prenda no encontrada", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"Prenda con ID 47 no encontrada\"}"))),

    @ApiResponse(responseCode = "409", description = "Conflicto en la solicitud", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"No se puede aumentar el stock de una prenda que no existe\"}"))),

    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content (mediaType = "application/json", 
    examples = @ExampleObject(value = "{\"Mensaje\": \"Ocurrió un error interno del servidor\"}")))
    })
    public ResponseEntity<String> aumentar(
        @Parameter(description = "ID de la prenda a aumentar", example = "1")
        @PathVariable Integer id,
        @Parameter(description = "Cantidad a aumentar del stock", example = "2")
        @RequestParam Integer cantidad) {
    prendasService.aumentarStock(id, cantidad);
    return ResponseEntity.ok("Stock aumentado exitosamente en Inventario");
}
}
