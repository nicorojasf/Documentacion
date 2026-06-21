package com.Inventario.Inventario.v2;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Inventario.Inventario.model.Prendas;
import com.Inventario.Inventario.service.PrendasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/prendas")
@Tag(name = "Prendas V2", description = "Operaciones HATEOAS v2 para prendas en el inventario de la clínica")
public class PrendasControllerV2 {

    private final PrendasService prendasService;
    private final PrendasModelAssemblerV2 prendasAssembler;

    @GetMapping("/{id}")
    @Operation(summary = "Obtener prenda por ID (V2)", description = "Obtiene una prenda con enlaces HATEOAS por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prenda encontrada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrendasModelV2.class))),
        @ApiResponse(responseCode = "404", description = "Prenda no encontrada", content = @Content(mediaType = "application/json", 
            examples = @ExampleObject(value = "{\"Mensaje\": \"Prenda con ID 47 no encontrada\"}")))
    })
    public ResponseEntity<PrendasModelV2> obtenerPorId(@Parameter(description = "ID de la prenda a obtener", example = "1")
                                                        @PathVariable("id") Integer id) {
        Prendas prenda = prendasService.obtenerPorId(id);
        return ResponseEntity.ok(prendasAssembler.toModel(prenda));
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todas las prendas (V2)", description = "Obtiene una colección HATEOAS con todas las prendas en el inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prendas encontradas exitosamente",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PrendasModelV2.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron prendas", content = @Content(mediaType = "application/json", 
            examples = @ExampleObject(value = "{\"Mensaje\": \"No se encontraron prendas registradas\"}")))
    })
    public ResponseEntity<CollectionModel<PrendasModelV2>> listarTodo() {
        List<Prendas> prendasList = prendasService.obtenerTodas();
        
        CollectionModel<PrendasModelV2> coleccion = prendasAssembler.toCollectionModel(prendasList);
        // Link self hacia el endpoint de la colección completa v2
        coleccion.add(linkTo(methodOn(PrendasControllerV2.class).listarTodo()).withSelfRel());
        
        return ResponseEntity.ok(coleccion);
    }

    @PostMapping("/guardar")
    @Operation(summary = "Guardar prenda (V2)", description = "Guarda una nueva prenda y devuelve su representación HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Prenda guardada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrendasModelV2.class)))
    })
    public ResponseEntity<PrendasModelV2> guardar(@RequestBody Prendas prendas) {
        Prendas nuevaPrenda = prendasService.guardar(prendas);
        return ResponseEntity.status(201).body(prendasAssembler.toModel(nuevaPrenda));
    }

    @PutMapping("/{id}/descontar")
    @Operation(summary = "Descontar stock (V2)", description = "Descuenta cantidad especificada de stock")
    public ResponseEntity<PrendasModelV2> descontar(
            @Parameter(description = "ID de la prenda a descontar", example = "1") @PathVariable Integer id,
        @Parameter(description = "Cantidad a descontar del stock", example = "2") @RequestParam Integer cantidad) {
    
    // 1. Modifica/Disminuye el stock en la base de datos
    prendasService.descontarStock(id, cantidad);
    
    // 2. Recupera la entidad con los nuevos valores reflejados
    Prendas prendaActualizada = prendasService.obtenerPorId(id);
    
    // 3. Convierte la entidad a tu modelo HATEOAS y la retorna con estado 200 OK
    return ResponseEntity.ok(prendasAssembler.toModel(prendaActualizada));
    }

    @PutMapping("/{id}/aumentar")
    @Operation(summary = "Aumentar stock (V2)", description = "Aumenta cantidad especificada de stock")
    public ResponseEntity<PrendasModelV2> aumentar(
            @Parameter(description = "ID de la prenda a aumentar", example = "1") @PathVariable Integer id,
        @Parameter(description = "Cantidad a aumentar del stock", example = "2") @RequestParam Integer cantidad) {
        prendasService.aumentarStock(id, cantidad);
    
    Prendas prendaActualizada = prendasService.obtenerPorId(id);
    
    return ResponseEntity.ok(prendasAssembler.toModel(prendaActualizada));}
    
}
