package com.example.demo.Controller;

import com.example.demo.Domain.Consultas.AgendaDeConsultasService;
import com.example.demo.Domain.Consultas.DadosCadastroAgendamentoConsulta;
import com.example.demo.Domain.Consultas.DadosDetalhamentoConsulta;
import com.example.demo.Domain.Medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosCadastroAgendamentoConsulta> dadosCadastroAgendamentoConsulta;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsulta;

    @MockBean
    private AgendaDeConsultasService service;

    @Test
    @DisplayName("devolver 400 se o corpo da requisição estiver inválido")
    @WithMockUser
    void agendar() throws Exception {
        var resaponse = mockMvc.perform(post("/consultas/agendar")).andReturn().getResponse();

        assertThat(resaponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("devolver 200 se o corpo da requisição estiver presente")
    @WithMockUser
    void agendar_cenario2() throws Exception {

        var data = LocalDateTime.now().plusDays(1);
        var especialidade = Especialidade.GINECOLOGIA;

        var dadosDetalhamento = new DadosDetalhamentoConsulta(1L, 1L, 1L, data, especialidade);


        when(service.agendarConsulta(any()))
                .thenReturn(
                        dadosDetalhamento
                );

        var resaponse = mockMvc.perform(post("/consultas/agendar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroAgendamentoConsulta.write(new DadosCadastroAgendamentoConsulta(1L, 1L, data, especialidade)).getJson())
                )
                .andReturn().getResponse();

        assertThat(resaponse.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonFinal = dadosDetalhamentoConsulta.write(dadosDetalhamento).getJson();
        assertThat(resaponse.getContentAsString()).isEqualTo(jsonFinal);

    }

}