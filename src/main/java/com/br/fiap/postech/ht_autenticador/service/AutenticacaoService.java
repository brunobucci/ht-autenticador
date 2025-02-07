package com.br.fiap.postech.ht_autenticador.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.fiap.postech.ht_autenticador.dto.UsuarioLoginDto;
import com.br.fiap.postech.ht_autenticador.dto.UsuarioRegistroDto;
import com.br.fiap.postech.ht_autenticador.entity.UsuarioEntity;
import com.br.fiap.postech.ht_autenticador.repository.UsuarioRepository;

@Service
public class AutenticacaoService {
    private final UsuarioRepository usuarioRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AutenticacaoService(
    	UsuarioRepository usuarioRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioEntity registrarUsuario(UsuarioRegistroDto usuarioRegistroDto) {
    	
    	Optional<UsuarioEntity> usuario = usuarioRepository.findUsuarioByUsername(usuarioRegistroDto.getUsername());

    	if(usuario.isEmpty()) {
    		UsuarioEntity usuarioEntity = new UsuarioEntity();
    		usuarioEntity.setNomeCompleto(usuarioRegistroDto.getNomeCompleto());
    		usuarioEntity.setUsername(usuarioRegistroDto.getUsername());
    		usuarioEntity.setPassword(passwordEncoder.encode(usuarioRegistroDto.getPassword()));
    		
    		return usuarioRepository.save(usuarioEntity);
    	}
    	return null;
    }

    public UsuarioEntity autenticarUsuario(UsuarioLoginDto usuarioLoginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		usuarioLoginDto.getUsername(),
                		usuarioLoginDto.getPassword()
                )
        );

        return usuarioRepository.findUsuarioByUsername(usuarioLoginDto.getUsername())
                .orElseThrow();
    }
}
