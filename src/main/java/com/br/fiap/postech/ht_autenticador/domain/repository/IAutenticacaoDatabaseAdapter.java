package com.br.fiap.postech.ht_autenticador.domain.repository;

import com.br.fiap.postech.ht_autenticador.application.dto.UsuarioLoginDto;
import com.br.fiap.postech.ht_autenticador.domain.entity.Usuario;
import com.br.fiap.postech.ht_autenticador.infra.database.entity.UsuarioEntity;

public interface IAutenticacaoDatabaseAdapter {
	 Usuario registrarUsuario(Usuario usuario);
	 UsuarioEntity autenticarUsuario(UsuarioLoginDto usuarioLoginDto);
}
