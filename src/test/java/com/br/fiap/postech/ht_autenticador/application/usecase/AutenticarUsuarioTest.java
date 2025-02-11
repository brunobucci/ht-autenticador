package com.br.fiap.postech.ht_autenticador.application.usecase;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.br.fiap.postech.ht_autenticador.application.dto.UsuarioLoginDto;
import com.br.fiap.postech.ht_autenticador.infra.database.entity.UsuarioEntity;
import com.br.fiap.postech.ht_autenticador.domain.repository.IAutenticacaoDatabaseAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class AutenticarUsuarioTest {

    @Mock
    private IAutenticacaoDatabaseAdapter autenticacaoDatabaseAdapter;

    @InjectMocks
    private AutenticarUsuario autenticarUsuario;

    private UsuarioLoginDto usuarioLoginDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa os mocks
        usuarioLoginDto = new UsuarioLoginDto();
        usuarioLoginDto.setUsername("joao.silva");
        usuarioLoginDto.setPassword("senha123");
    }

    @Test
    void testExecutar() {
        // Configurando o comportamento do mock
        UsuarioEntity usuarioEntity = new UsuarioEntity("1", "João da Silva", "joao.silva", "senha123");
        when(autenticacaoDatabaseAdapter.autenticarUsuario(any(UsuarioLoginDto.class))).thenReturn(usuarioEntity);

        // Chama o método a ser testado
        UsuarioEntity resultado = autenticarUsuario.executar(usuarioLoginDto);

        // Verifica se o resultado não é nulo
        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        assertEquals("João da Silva", resultado.getNomeCompleto());
        assertEquals("joao.silva", resultado.getUsername());
        assertEquals("senha123", resultado.getPassword());

        // Verifica se o método 'autenticarUsuario' foi chamado uma vez com o parâmetro correto
        verify(autenticacaoDatabaseAdapter, times(1)).autenticarUsuario(any(UsuarioLoginDto.class));
    }

    @Test
    void testExecutarUsuarioNaoAutenticado() {
        // Simula o caso em que o usuário não é encontrado ou as credenciais são inválidas
        when(autenticacaoDatabaseAdapter.autenticarUsuario(any(UsuarioLoginDto.class))).thenReturn(null);

        // Chama o método a ser testado
        UsuarioEntity resultado = autenticarUsuario.executar(usuarioLoginDto);

        // Verifica se o resultado é nulo, indicando que a autenticação falhou
        assertNull(resultado);

        // Verifica se o método 'autenticarUsuario' foi chamado uma vez
        verify(autenticacaoDatabaseAdapter, times(1)).autenticarUsuario(any(UsuarioLoginDto.class));
    }
}