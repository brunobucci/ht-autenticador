package com.br.fiap.postech.ht_autenticador.application.configs;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.br.fiap.postech.ht_autenticador.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HandlerExceptionResolver handlerExceptionResolver;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userDetailsService, handlerExceptionResolver);
    }

    @Test
    void testDoFilterInternal_NoAuthorizationHeader() throws Exception {
        // Cenário: Sem o cabeçalho de autorização.
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verifica se o filtro continua a execução do filtro chain
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_InvalidToken() throws Exception {
        // Cenário: Cabeçalho de autorização com token inválido
        String invalidJwt = "invalidToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidJwt);
        when(jwtService.extractUsername(invalidJwt)).thenReturn("username");
        when(userDetailsService.loadUserByUsername("username")).thenReturn(userDetails);
        when(jwtService.isTokenValid(invalidJwt, userDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verifica que o filtro segue o fluxo e não autentica
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_ValidToken() throws Exception {
        // Cenário: Cabeçalho de autorização com token válido
        String validJwt = "validToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validJwt);
        when(jwtService.extractUsername(validJwt)).thenReturn("username");
        when(userDetailsService.loadUserByUsername("username")).thenReturn(userDetails);
        when(jwtService.isTokenValid(validJwt, userDetails)).thenReturn(true);

        // Cria um mock do Authentication
        Authentication authToken = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(null); // Garantir que o contexto não tenha autenticação

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verifica se o Authentication foi criado e colocado no contexto de segurança
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }
}
