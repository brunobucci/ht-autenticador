package com.br.fiap.postech.ht_autenticador.application.usecase;

import org.springframework.stereotype.Service;

import com.br.fiap.postech.ht_autenticador.application.dto.UsuarioLoginDto;
import com.br.fiap.postech.ht_autenticador.application.dto.UsuarioRegistroDto;
import com.br.fiap.postech.ht_autenticador.domain.entity.Usuario;
import com.br.fiap.postech.ht_autenticador.domain.repository.IAutenticacaoDatabaseAdapter;
import com.br.fiap.postech.ht_autenticador.domain.usecase.AutenticarUsuarioUsecase;
import com.br.fiap.postech.ht_autenticador.infra.database.entity.UsuarioEntity;

@Service
public class AutenticarUsuario implements AutenticarUsuarioUsecase{

	private final IAutenticacaoDatabaseAdapter autenticacaoDatabaseAdapter;
    
    public AutenticarUsuario(IAutenticacaoDatabaseAdapter autenticacaoDatabaseAdapter) {
    	this.autenticacaoDatabaseAdapter = autenticacaoDatabaseAdapter;
    }
	
	@Override
	public UsuarioEntity executar(UsuarioLoginDto usuarioLoginDto) {
		UsuarioEntity usuarioSalvo = autenticacaoDatabaseAdapter.autenticarUsuario(usuarioLoginDto);
		return usuarioSalvo;
	}

	private UsuarioRegistroDto toUsuarioRegistroDto(Usuario usuarioSalvo) {
		// TODO Auto-generated method stub
		return null;
	}
}