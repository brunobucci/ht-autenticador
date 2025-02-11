package com.br.fiap.postech.ht_autenticador.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.fiap.postech.ht_autenticador.dto.UsuarioLoginDto;
import com.br.fiap.postech.ht_autenticador.dto.UsuarioLoginResponseDto;
import com.br.fiap.postech.ht_autenticador.dto.UsuarioRegistroDto;
import com.br.fiap.postech.ht_autenticador.entity.UsuarioEntity;
import com.br.fiap.postech.ht_autenticador.service.AutenticacaoService;
import com.br.fiap.postech.ht_autenticador.service.JwtService;

@RequestMapping("/auth")
@RestController
public class AutenticacaoController {
    private final JwtService jwtService;
    
    private final AutenticacaoService autenticacaoService;

    public AutenticacaoController(JwtService jwtService, AutenticacaoService autenticacaoService) {
        this.jwtService = jwtService;
        this.autenticacaoService = autenticacaoService;
    }
    
    @PostMapping("/registra")
    public ResponseEntity<UsuarioEntity> registrarUsuario(@RequestBody UsuarioRegistroDto usuarioRegistroDto) {
    	UsuarioEntity usuarioRegistrado = autenticacaoService.registrarUsuario(usuarioRegistroDto);

        return ResponseEntity.ok(usuarioRegistrado);
    }

    @PostMapping("/autentica")
    public ResponseEntity<UsuarioLoginResponseDto> autenticarUsuario(@RequestBody UsuarioLoginDto usuarioLoginDto) {
    	UsuarioEntity authenticatedUser = autenticacaoService.autenticarUsuario(usuarioLoginDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        UsuarioLoginResponseDto usuarioLoginResponseDto = new UsuarioLoginResponseDto();
        usuarioLoginResponseDto.setToken(jwtToken);
        usuarioLoginResponseDto.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(usuarioLoginResponseDto);
    }
}