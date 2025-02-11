package com.br.fiap.postech.ht_autenticador.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

class JwtServiceTest {

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtService jwtService;

    private String token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        jwtService.secretKey = "0fd497b17ac4dcf4d4c7aebb9a14a4a5e6d0fb2b96d36d0cbcb0564ec9d67206"; // Chave secreta para o JWT
        jwtService.jwtExpiration = 3600000; // Tempo de expiração (1 hora, por exemplo)

        userDetails = User.builder()
                .username("user123")
                .password("password123")
                .roles("USER")
                .build();

        token = jwtService.generateToken(userDetails);
    }

    @Test
    void testGenerateToken() {
        // Gerar o token
        String generatedToken = jwtService.generateToken(userDetails);

        // Verificar se o token não é nulo e possui um formato válido (não nulo e não vazio)
        assertNotNull(generatedToken);
        assertFalse(generatedToken.isEmpty());
    }

    @Test
    void testExtractUsername() {
        // Extraímos o nome de usuário do token gerado
        String username = jwtService.extractUsername(token);

        // Verificar se o nome de usuário extraído é o mesmo que foi usado para gerar o token
        assertEquals("user123", username);
    }

    @Test
    void testIsTokenValid() {
        // Verificar se o token gerado é válido para o usuárioDetails fornecido
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Verificar que o token gerado é válido
        assertTrue(isValid);
    }

    @Test
    void testIsTokenExpired() {
        // Alterando a expiração para testar a expiração do token
        // Espera-se que o token não esteja expirado já que estamos dentro do tempo de vida do token
        boolean isExpired = jwtService.isTokenExpired(token);

        // Verifica se o token não está expirado
        assertFalse(isExpired);
    }

    @Test
    void testExtractExpiration() {
        Date expirationDate = jwtService.extractExpiration(token);

        // Verifica se a data de expiração está sendo extraída corretamente
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    void testIsTokenInvalid() {
        // Alterando o username do UserDetails para um valor diferente
        UserDetails fakeUserDetails = User.builder()
                .username("fakeUser")
                .password("password")
                .roles("USER")
                .build();

        // Verificando se o token não é válido para um UserDetails com username diferente
        boolean isValid = jwtService.isTokenValid(token, fakeUserDetails);

        // O token não deve ser válido porque o username não bate
        assertFalse(isValid);
    }
}
