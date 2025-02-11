package com.br.fiap.postech.ht_autenticador.application.usecase;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.br.fiap.postech.ht_autenticador.application.dto.UsuarioRegistroDto;
import com.br.fiap.postech.ht_autenticador.domain.entity.Usuario;
import com.br.fiap.postech.ht_autenticador.domain.repository.IAutenticacaoDatabaseAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class RegistrarUsuarioTest {

    @Mock
    private IAutenticacaoDatabaseAdapter autenticacaoDatabaseAdapter;

    @InjectMocks
    private RegistrarUsuario registrarUsuario;

    private UsuarioRegistroDto usuarioRegistroDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa os mocks
        usuarioRegistroDto = new UsuarioRegistroDto();
        usuarioRegistroDto.setNomeCompleto("João da Silva");
        usuarioRegistroDto.setUsername("joao.silva");
        usuarioRegistroDto.setPassword("senha123");
    }

    @Test
    void testExecutar() {
        // Configurando o comportamento do mock
        Usuario usuarioSalvo = new Usuario("1", "João da Silva", "joao.silva", "senha123");
        when(autenticacaoDatabaseAdapter.registrarUsuario(any(Usuario.class))).thenReturn(usuarioSalvo);

        // Chama o método a ser testado
        UsuarioRegistroDto resultado = registrarUsuario.executar(usuarioRegistroDto);

        // Verifica o resultado
        assertNotNull(resultado);
        assertEquals("João da Silva", resultado.getNomeCompleto());
        assertEquals("joao.silva", resultado.getUsername());
        assertEquals("senha123", resultado.getPassword());

        // Verifica se o método 'registrarUsuario' foi chamado uma vez com o usuário esperado
        verify(autenticacaoDatabaseAdapter, times(1)).registrarUsuario(any(Usuario.class));
    }
}
