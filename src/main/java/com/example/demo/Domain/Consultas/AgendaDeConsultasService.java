package com.example.demo.Domain.Consultas;

import com.example.demo.Domain.Consultas.Validations.ValidadorAgendamentoDeConsultas;
import com.example.demo.Domain.Medico.Medico;
import com.example.demo.Repository.ConsultasRepository;
import com.example.demo.Repository.MedicoRepository;
import com.example.demo.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultasService {

    @Autowired
    private ConsultasRepository consultasRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsultas> validadores;

    public DadosDetalhamentoConsulta agendarConsulta(DadosCadastroAgendamentoConsulta dados) {
        validadores.forEach(validador -> validador.validar(dados));
        var paciente = pacienteRepository.findById(dados.idPaciente()).orElseThrow(NullPointerException::new);
        var medico = ChooseDoctorOrRadom(dados);
        var consulta = new Consultas(null, medico,paciente,dados.data());
         var savedDta = consultasRepository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico ChooseDoctorOrRadom(DadosCadastroAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.findById(dados.idMedico()).get();
        }

        if (dados.especialidade() == null) {
            throw new NullPointerException("Especialidade não informada");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}
