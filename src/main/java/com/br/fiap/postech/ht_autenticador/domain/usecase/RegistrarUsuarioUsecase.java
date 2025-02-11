package com.br.fiap.postech.ht_autenticador.domain.usecase;

import com.br.fiap.postech.ht_autenticador.application.dto.UsuarioRegistroDto;

public interface RegistrarUsuarioUsecase {
	UsuarioRegistroDto executar(UsuarioRegistroDto usuarioRegistroDto);
}
