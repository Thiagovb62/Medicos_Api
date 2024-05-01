package com.example.demo.MedicoTeste;

import com.example.demo.Domain.Consultas.Consultas;
import com.example.demo.Domain.Medico.DadosCadastroMedicos;
import com.example.demo.Domain.Medico.DadosEndereco;
import com.example.demo.Domain.Medico.Especialidade;
import com.example.demo.Domain.Medico.Medico;
import com.example.demo.Domain.Paciente.DadosCadastroPaciente;
import com.example.demo.Domain.Paciente.Paciente;
import com.example.demo.Repository.MedicoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;


    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Devolver null se não houver médico disponível na data")
    void escolherMedicoLivreNaDataCenario1() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.GINECOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.GINECOLOGIA, proximaSegundaAs10);
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Devolver medico disponível na data")
    void escolherMedicoLivreNaDataCenario2() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.GINECOLOGIA);
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.GINECOLOGIA, proximaSegundaAs10);
        assertThat(medicoLivre).isEqualTo(medico);
    }

    @Test
    @DisplayName("Devolver medico ativo por id")
    void escolherMedicoAtivoPorIdCenario1() {
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.GINECOLOGIA);
        var medicoLivre = medicoRepository.findAtivoById(medico.getId());
        assertThat(medicoLivre).isTrue();
    }

    @Test
    @DisplayName("Devolver medico inativo por id")
    void escolherMedicoInativoPorIdCenario1() {
        var medico = cadastrarMedico("Medico", "medico@voll.med", "12345", Especialidade.GINECOLOGIA);
        medico.setAtivo(false);
        var medicoLivre = medicoRepository.findAtivoById(medico.getId());
        assertThat(medicoLivre).isFalse();
    }
    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consultas(null, medico, paciente, data));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedicos dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedicos(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "61999999999",
                true,
                cpf,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}
