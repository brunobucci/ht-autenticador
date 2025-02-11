package com.br.fiap.postech.ht_autenticador.infra.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.br.fiap.postech.ht_autenticador.application.dto.UsuarioLoginDto;
import com.br.fiap.postech.ht_autenticador.domain.entity.Usuario;
import com.br.fiap.postech.ht_autenticador.infra.database.entity.UsuarioEntity;

class AutenticacaoDatabaseAdapterTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AutenticacaoDatabaseAdapter autenticacaoDatabaseAdapter;

    private Usuario usuario;
    private UsuarioEntity usuarioEntity;
    private UsuarioLoginDto usuarioLoginDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Criação de objetos fictícios
        usuario = new Usuario(null, "João da Silva", "joao.silva", "senha123");
        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId("1");
        usuarioEntity.setNomeCompleto("João da Silva");
        usuarioEntity.setUsername("joao.silva");
        usuarioEntity.setPassword("senha123");
        
        usuarioLoginDto = new UsuarioLoginDto();
        usuarioLoginDto.setUsername("joao.silva");
        usuarioLoginDto.setPassword("senha123");
    }

    @Test
    void testRegistrarUsuarioSucesso() {
        // Configura o comportamento do mock do repositório
        when(usuarioRepository.findUsuarioByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("senhaCodificada");

        // Chama o método de registrar
        Usuario resultado = autenticacaoDatabaseAdapter.registrarUsuario(usuario);

        // Verifica se o resultado não é nulo e se os dados estão corretos
        assertNotNull(resultado);
        assertEquals("João da Silva", resultado.getNomeCompleto());
        assertEquals("joao.silva", resultado.getUsername());

        // Verifica se o repositório foi chamado corretamente
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    void testRegistrarUsuarioJaExiste() {
        // Configura o comportamento do mock para quando o usuário já existir
        when(usuarioRepository.findUsuarioByUsername(anyString())).thenReturn(Optional.of(usuarioEntity));

        // Chama o método de registrar
        Usuario resultado = autenticacaoDatabaseAdapter.registrarUsuario(usuario);

        // Verifica se o resultado é nulo, pois o usuário já existe
        assertNull(resultado);

        // Verifica se o repositório não foi chamado para salvar
        verify(usuarioRepository, times(0)).save(any(UsuarioEntity.class));
    }

    @Test
    void testAutenticarUsuarioSucesso() {
        // Configura o comportamento do mock do authenticationManager
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(usuarioRepository.findUsuarioByUsername(anyString())).thenReturn(Optional.of(usuarioEntity));

        // Chama o método de autenticar
        UsuarioEntity resultado = autenticacaoDatabaseAdapter.autenticarUsuario(usuarioLoginDto);

        // Verifica se o resultado não é nulo e se os dados estão corretos
        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        assertEquals("João da Silva", resultado.getNomeCompleto());
        assertEquals("joao.silva", resultado.getUsername());
    }

    @Test
    void testAutenticarUsuarioUsuarioNaoEncontrado() {
        // Configura o comportamento do mock do authenticationManager
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(usuarioRepository.findUsuarioByUsername(anyString())).thenReturn(Optional.empty());

        // Chama o método de autenticar e espera que uma exceção seja lançada
        assertThrows(NoSuchElementException.class, () -> {
            autenticacaoDatabaseAdapter.autenticarUsuario(usuarioLoginDto);
        });

        // Verifica se o método de autenticação foi chamado
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
