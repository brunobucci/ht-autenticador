package com.br.fiap.postech.ht_autenticador.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
	private String id;
	private String nomeCompleto;
	private String username;
	private String password;
}
