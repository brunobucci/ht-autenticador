package com.br.fiap.postech.ht_autenticador.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginResponseDto {

	private String token;
    private long expiresIn;

}