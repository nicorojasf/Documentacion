package com.Inventario.Inventario.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Inventario.Inventario.model.Prendas;
import com.Inventario.Inventario.repository.PrendasRepository;
import com.Inventario.Inventario.util.PrendasTestDataFactory;


@ExtendWith(MockitoExtension.class)
public class PrendasServiceTest {

    @Mock
    private PrendasRepository prendasRepository;

    @InjectMocks
    private PrendasService prendasService;

    @Test
    @DisplayName("findAll debe retornar la lista de prendas")
    void findAllDeberiaRetornarPrendas() {
        // Given
        Prendas p1 = PrendasTestDataFactory.crearPrendaValida();
        when(prendasRepository.findAll()).thenReturn(List.of(p1));

        // When
        List<Prendas> resultado = prendasService.obtenerTodas();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Kimono", resultado.get(0).getNombrePrenda());
        verify(prendasRepository).findAll();
    }

    @Test
    @DisplayName("findById debe retornar prenda cuando existe")
    void findByIdDeberiaRetornarPrendaCuandoExiste() {
        // Given
        Prendas prenda = PrendasTestDataFactory.crearPrendaValida();
        when(prendasRepository.findById(1)).thenReturn(Optional.of(prenda));

        // When
        Prendas resultado = prendasService.obtenerPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals("Kimono", resultado.getNombrePrenda());
        verify(prendasRepository).findById(1);
    }
    
}
