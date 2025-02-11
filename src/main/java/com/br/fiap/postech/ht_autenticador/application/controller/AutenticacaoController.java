package com.br.fiap.postech.ht_autenticador.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.fiap.postech.ht_autenticador.application.dto.UsuarioLoginDto;
import com.br.fiap.postech.ht_autenticador.application.dto.UsuarioLoginResponseDto;
import com.br.fiap.postech.ht_autenticador.application.dto.UsuarioRegistroDto;
import com.br.fiap.postech.ht_autenticador.domain.usecase.AutenticarUsuarioUsecase;
import com.br.fiap.postech.ht_autenticador.domain.usecase.RegistrarUsuarioUsecase;
import com.br.fiap.postech.ht_autenticador.infra.database.entity.UsuarioEntity;
import com.br.fiap.postech.ht_autenticador.service.JwtService;

@RequestMapping("/auth")
@RestController
public class AutenticacaoController {

	private final RegistrarUsuarioUsecase registrarUsuarioUsecase;
	private final AutenticarUsuarioUsecase autenticarUsuarioUsecase;
	
	private final JwtService jwtService;
    
    @Autowired
    public AutenticacaoController(RegistrarUsuarioUsecase registrarUsuarioUsecase, AutenticarUsuarioUsecase autenticarUsuarioUsecase, JwtService jwtService) {
    	this.registrarUsuarioUsecase = registrarUsuarioUsecase;
    	this.autenticarUsuarioUsecase = autenticarUsuarioUsecase;
    	this.jwtService = jwtService;
    }
    
    @PostMapping("/registra")
    public ResponseEntity<UsuarioRegistroDto> registrarUsuario(@RequestBody UsuarioRegistroDto usuarioRegistroDto) {
    	UsuarioRegistroDto usuarioRegistrado = registrarUsuarioUsecase.executar(usuarioRegistroDto);
        return ResponseEntity.ok(usuarioRegistrado);
    }

    @PostMapping("/autentica")
    public ResponseEntity<UsuarioLoginResponseDto> autenticarUsuario(@RequestBody UsuarioLoginDto usuarioLoginDto) {
    	UsuarioEntity authenticatedUser = autenticarUsuarioUsecase.executar(usuarioLoginDto);
    	
        String jwtToken = jwtService.generateToken(authenticatedUser);

        UsuarioLoginResponseDto usuarioLoginResponseDto = new UsuarioLoginResponseDto();
        usuarioLoginResponseDto.setToken(jwtToken);
        usuarioLoginResponseDto.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(usuarioLoginResponseDto);
    }
}