package com.br.fiap.postech.ht_autenticador.application.usecase;

import org.springframework.stereotype.Service;

import com.br.fiap.postech.ht_autenticador.application.dto.UsuarioRegistroDto;
import com.br.fiap.postech.ht_autenticador.domain.entity.Usuario;
import com.br.fiap.postech.ht_autenticador.domain.repository.IAutenticacaoDatabaseAdapter;
import com.br.fiap.postech.ht_autenticador.domain.usecase.RegistrarUsuarioUsecase;

@Service
public class RegistrarUsuario implements RegistrarUsuarioUsecase{

	private final IAutenticacaoDatabaseAdapter autenticacaoDatabaseAdapter;
    
    public RegistrarUsuario(IAutenticacaoDatabaseAdapter autenticacaoDatabaseAdapter) {
    	this.autenticacaoDatabaseAdapter = autenticacaoDatabaseAdapter;
    }
	
	@Override
	public UsuarioRegistroDto executar(UsuarioRegistroDto usuarioRegistroDto) {
		Usuario usuario = new Usuario(null, usuarioRegistroDto.getNomeCompleto(), usuarioRegistroDto.getUsername(), usuarioRegistroDto.getPassword());
		Usuario usuarioSalvo = autenticacaoDatabaseAdapter.registrarUsuario(usuario);
		return toUsuarioRegistroDto(usuarioSalvo);
	}

	private UsuarioRegistroDto toUsuarioRegistroDto(Usuario usuario) {
		UsuarioRegistroDto usuarioRegistroDto = new UsuarioRegistroDto();
		usuarioRegistroDto.setNomeCompleto(usuario.getNomeCompleto());
		usuarioRegistroDto.setUsername(usuario.getUsername());
		usuarioRegistroDto.setPassword(usuario.getPassword());
		return usuarioRegistroDto;
	}

}
