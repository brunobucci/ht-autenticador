package com.br.fiap.postech.ht_autenticador.domain.usecase;

import com.br.fiap.postech.ht_autenticador.application.dto.UsuarioLoginDto;
import com.br.fiap.postech.ht_autenticador.infra.database.entity.UsuarioEntity;

public interface AutenticarUsuarioUsecase {
	UsuarioEntity executar(UsuarioLoginDto usuarioLoginDto);
}
