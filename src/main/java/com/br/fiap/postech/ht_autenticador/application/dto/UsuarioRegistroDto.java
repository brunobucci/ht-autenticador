package com.br.fiap.postech.ht_autenticador.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRegistroDto {
	private String nomeCompleto;
	private String username;
	private String password;
}
