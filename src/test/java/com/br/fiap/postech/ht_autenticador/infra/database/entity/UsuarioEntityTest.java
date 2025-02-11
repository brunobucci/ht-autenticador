package com.br.fiap.postech.ht_autenticador.infra.database.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

class UsuarioEntityTest {

    private UsuarioEntity usuarioEntity;

    @BeforeEach
    void setUp() {
        // Setup do objeto UsuarioEntity para os testes
        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId("123");
        usuarioEntity.setNomeCompleto("John Doe");
        usuarioEntity.setUsername("john_doe");
        usuarioEntity.setPassword("password123");
    }

    @Test
    void testGetAuthorities() {
        // Chama o método getAuthorities e verifica se retorna uma lista vazia
        Collection<? extends GrantedAuthority> authorities = usuarioEntity.getAuthorities();
        assertNotNull(authorities);
        assertTrue(authorities.isEmpty(), "As autoridades devem estar vazias");
    }

    @Test
    void testIsAccountNonExpired() {
        // Chama o método isAccountNonExpired e verifica se retorna true
        boolean isAccountNonExpired = usuarioEntity.isAccountNonExpired();
        assertTrue(isAccountNonExpired, "A conta não deve estar expirada");
    }

    @Test
    void testIsAccountNonLocked() {
        // Chama o método isAccountNonLocked e verifica se retorna true
        boolean isAccountNonLocked = usuarioEntity.isAccountNonLocked();
        assertTrue(isAccountNonLocked, "A conta não deve estar bloqueada");
    }

    @Test
    void testIsCredentialsNonExpired() {
        // Chama o método isCredentialsNonExpired e verifica se retorna true
        boolean isCredentialsNonExpired = usuarioEntity.isCredentialsNonExpired();
        assertTrue(isCredentialsNonExpired, "As credenciais não devem estar expiradas");
    }

    @Test
    void testIsEnabled() {
        // Chama o método isEnabled e verifica se retorna true
        boolean isEnabled = usuarioEntity.isEnabled();
        assertTrue(isEnabled, "O usuário deve estar habilitado");
    }

    @Test
    void testGetUsername() {
        // Chama o método getUsername e verifica se retorna o nome de usuário correto
        String username = usuarioEntity.getUsername();
        assertEquals("john_doe", username, "O nome de usuário deve ser 'john_doe'");
    }

    @Test
    void testGetPassword() {
        // Chama o método getPassword e verifica se retorna a senha correta
        String password = usuarioEntity.getPassword();
        assertEquals("password123", password, "A senha deve ser 'password123'");
    }

    @Test
    void testGetNomeCompleto() {
        // Chama o método getNomeCompleto e verifica se retorna o nome completo correto
        String nomeCompleto = usuarioEntity.getNomeCompleto();
        assertEquals("John Doe", nomeCompleto, "O nome completo deve ser 'John Doe'");
    }

    @Test
    void testSettersAndGetters() {
        // Testando os setters e getters
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId("456");
        usuarioEntity.setNomeCompleto("Jane Doe");
        usuarioEntity.setUsername("jane_doe");
        usuarioEntity.setPassword("password456");

        assertEquals("456", usuarioEntity.getId());
        assertEquals("Jane Doe", usuarioEntity.getNomeCompleto());
        assertEquals("jane_doe", usuarioEntity.getUsername());
        assertEquals("password456", usuarioEntity.getPassword());
    }
}
