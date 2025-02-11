package com.br.fiap.postech.ht_autenticador;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class AutenticacaoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private String payloadLoginValido = "{\n  \"username\": \"marceloquevedo\",\n  \"password\": \"marcelo#123\"\n}";

	//private String payloadLoginInvalido = "{\n  \"username\": \"loquevedo\",\n  \"password\": \"lo#123\"\n}";
	
	private String payloadRegistroValido = "{\n  \"nomeCompleto\": \"Marcelo Quevedo\",\n  \"username\": \"marceloquevedo\",\n  \"password\": \"marcelo#123\" \n}";
	
    @Test
    void dadasCredenciaisDeUsuarioValidas_quandoExecutadoEndpointDeLogin_deveRetornar200() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders.post("/auth/autentica").content(payloadLoginValido).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
    }

//    @Test
//    void dadasCredenciaisDeUsuarioInvalidas_quandoExecutadoEndpointDeLogin_deveRetornar4xx() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/auth/autentica").content(payloadLoginInvalido).contentType(MediaType.APPLICATION_JSON)
//        		.accept(MediaType.APPLICATION_JSON))
//          .andExpect(status().is4xxClientError());
//    }
    
    @Test
    void dadasDadosDeUsuarioValidos_quandoExecutadoEndpointDeRegistro_deveRetornar200() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders.post("/auth/registra").content(payloadRegistroValido).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
    }
    
//    @Test
//    void dadasDadosDeUsuarioInvalidos_quandoExecutadoEndpointDeRegistro_deveRetornar4xx() throws Exception {
//    	mockMvc.perform(MockMvcRequestBuilders.post("/auth/registra").content(payloadRegistroInvalido).contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//          .andExpect(status().isBadRequest());
//    }


//    @Test
//    void givenUserCredentials_whenInvokeAdminAuthorizedEndPoint_thenReturn403() throws Exception {
//        mvc.perform(get("/admin").with(httpBasic(USER_NAME, PASSWORD)))
//          .andExpect(status().isForbidden());
//    }
//
//    @Test
//    void givenAdminCredentials_whenInvokeAdminAuthorizedEndPoint_thenReturn200() throws Exception {
//        mvc.perform(get("/admin").with(httpBasic(ADMIN_NAME, PASSWORD)))
//          .andExpect(status().isOk());
//
//        mvc.perform(get("/user").with(httpBasic(ADMIN_NAME, PASSWORD)))
//          .andExpect(status().isOk());
//    }
	
}
