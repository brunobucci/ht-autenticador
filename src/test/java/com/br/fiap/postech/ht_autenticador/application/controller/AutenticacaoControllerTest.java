package com.br.fiap.postech.ht_autenticador.application.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.br.fiap.postech.ht_autenticador.application.dto.UsuarioLoginDto;
import com.br.fiap.postech.ht_autenticador.application.dto.UsuarioLoginResponseDto;
import com.br.fiap.postech.ht_autenticador.application.dto.UsuarioRegistroDto;
import com.br.fiap.postech.ht_autenticador.domain.usecase.AutenticarUsuarioUsecase;
import com.br.fiap.postech.ht_autenticador.domain.usecase.RegistrarUsuarioUsecase;
import com.br.fiap.postech.ht_autenticador.service.JwtService;
import com.br.fiap.postech.ht_autenticador.infra.database.entity.UsuarioEntity;

class AutenticacaoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RegistrarUsuarioUsecase registrarUsuarioUsecase;

    @Mock
    private AutenticarUsuarioUsecase autenticarUsuarioUsecase;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AutenticacaoController autenticacaoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(autenticacaoController).build();
    }

    @Test
    void testRegistrarUsuario() throws Exception {
        // Dado
        UsuarioRegistroDto usuarioRegistroDto = new UsuarioRegistroDto("John Doe", "john", "password123");
        UsuarioRegistroDto usuarioRegistrado = new UsuarioRegistroDto("John Doe", "john", "password123");
        
        when(registrarUsuarioUsecase.executar(usuarioRegistroDto)).thenReturn(usuarioRegistrado);

        // Quando
        ResponseEntity<UsuarioRegistroDto> response = autenticacaoController.registrarUsuario(usuarioRegistroDto);

        // Então
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(usuarioRegistroDto.getUsername(), response.getBody().getUsername());
        verify(registrarUsuarioUsecase, times(1)).executar(usuarioRegistroDto);
    }

    @Test
    void testAutenticarUsuario() throws Exception {
        // Dado
        UsuarioLoginDto usuarioLoginDto = new UsuarioLoginDto("john", "password123");
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setUsername("john");
        usuarioEntity.setPassword("password123");

        String token = "jwt-token";
        when(autenticarUsuarioUsecase.executar(usuarioLoginDto)).thenReturn(usuarioEntity);
        when(jwtService.generateToken(usuarioEntity)).thenReturn(token);
        when(jwtService.getExpirationTime()).thenReturn(3600000L); 

        // Quando
        ResponseEntity<UsuarioLoginResponseDto> response = autenticacaoController.autenticarUsuario(usuarioLoginDto);

        // Então
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(token, response.getBody().getToken());
        assertEquals(3600000L, response.getBody().getExpiresIn());
        verify(autenticarUsuarioUsecase, times(1)).executar(usuarioLoginDto);
        verify(jwtService, times(1)).generateToken(usuarioEntity);
    }
}
