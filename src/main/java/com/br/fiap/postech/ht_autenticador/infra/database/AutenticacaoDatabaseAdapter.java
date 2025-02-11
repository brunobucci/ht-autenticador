package com.br.fiap.postech.ht_autenticador.infra.database;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.fiap.postech.ht_autenticador.application.dto.UsuarioLoginDto;
import com.br.fiap.postech.ht_autenticador.domain.entity.Usuario;
import com.br.fiap.postech.ht_autenticador.domain.repository.IAutenticacaoDatabaseAdapter;
import com.br.fiap.postech.ht_autenticador.infra.database.entity.UsuarioEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutenticacaoDatabaseAdapter implements IAutenticacaoDatabaseAdapter {

	@Autowired
	private final UsuarioRepository usuarioRepository;
	@Autowired
    private final PasswordEncoder passwordEncoder;
	@Autowired
	private final AuthenticationManager authenticationManager;

    public Usuario registrarUsuario(Usuario usuario) {
    	
    	Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findUsuarioByUsername(usuario.getUsername());

    	if(usuarioEntity.isEmpty()) {
    		UsuarioEntity usuarioNovo = new UsuarioEntity();
    		usuarioNovo.setNomeCompleto(usuario.getNomeCompleto());
    		usuarioNovo.setUsername(usuario.getUsername());
    		usuarioNovo.setPassword(passwordEncoder.encode(usuario.getPassword()));
    		
    		 usuarioRepository.save(usuarioNovo);
    		 return toUsuario(usuarioNovo);
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

    private Usuario toUsuario(UsuarioEntity usuarioEntity) {
    	Usuario usuario = null;
    	usuario = new Usuario(usuarioEntity.getId(),
				usuarioEntity.getNomeCompleto(),
				usuarioEntity.getUsername(),
				usuarioEntity.getPassword());
		
		return usuario;
	}
}
