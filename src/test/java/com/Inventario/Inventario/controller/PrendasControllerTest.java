package com.Inventario.Inventario.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.Inventario.Inventario.model.Prendas;
import com.Inventario.Inventario.service.PrendasService;
import com.Inventario.Inventario.util.PrendasTestDataFactory;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
@WebMvcTest(controllers = PrendasController.class)
public class PrendasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean 
    private PrendasService prendasService;

    @Test
    @DisplayName("GET /api/prendas/listar debe retornar 200 y lista de prendas")
    void listarTodoDeberiaRetornarOKyLista() throws Exception {
        Prendas prenda = PrendasTestDataFactory.crearPrendaValida();
        when(prendasService.obtenerTodas()).thenReturn(List.of(prenda));

        mockMvc.perform(get("/api/prendas/listar")
                .with(user("admin").roles("USER", "ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombrePrenda").value("Kimono"));

        verify(prendasService).obtenerTodas();
    }

    @Test
    @DisplayName("POST /api/prendas/guardar debe retornar 201 y la prenda creada")
    void guardarDeberiaRetornarCreatedYPrenda() throws Exception {
        Prendas prenda = PrendasTestDataFactory.crearPrendaValida();
        when(prendasService.guardar(any(Prendas.class))).thenReturn(prenda);

        mockMvc.perform(post("/api/prendas/guardar") // Para buscar el error 404 cambiamos el endpoint a /guardar-rutas
                .with(user("admin").roles("USER", "ADMIN"))
                .with(csrf()) 
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prenda))) 
                .andExpect(status().isCreated()) //CAMBIOS A ISNOTFOUND PARA VER EL ERROR 404
                .andExpect(jsonPath("$.nombrePrenda").value("Kimono"));

        verify(prendasService).guardar(any(Prendas.class));
    }


    @Test
    @DisplayName("PUT /api/prendas/{id}/descontar debe retornar 200 y mensaje de éxito")
    void descontarDeberiaRetornarOKyMensaje() throws Exception {
        // Given
        Integer idPrenda = 1;
        Integer cantidad = 5;
        doNothing().when(prendasService).descontarStock(idPrenda, cantidad);

        // When + Then
        mockMvc.perform(put("/api/prendas/" + idPrenda + "/descontar")
                .with(user("admin").roles("USER", "ADMIN")) // Salta seguridad
                .with(csrf())                               // Salta CSRF en PUT
                .param("cantidad", cantidad.toString())     // Pasa el @RequestParam
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock descontado exitosamente en Inventario"));

        // Verify
        verify(prendasService).descontarStock(idPrenda, cantidad);
    }

    @Test
    @DisplayName("PUT /api/prendas/{id}/aumentar debe retornar 200 y mensaje de éxito")
    void aumentarDeberiaRetornarOKyMensaje() throws Exception {
        // Given
        Integer idPrenda = 1;
        Integer cantidad = 10;
        
        doNothing().when(prendasService).aumentarStock(idPrenda, cantidad);

        // When + Then
        mockMvc.perform(put("/api/prendas/" + idPrenda + "/aumentar")
                .with(user("admin").roles("USER", "ADMIN"))
                .with(csrf())
                .param("cantidad", cantidad.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock aumentado exitosamente en Inventario"));

        // Verify
        verify(prendasService).aumentarStock(idPrenda, cantidad);
    }
}