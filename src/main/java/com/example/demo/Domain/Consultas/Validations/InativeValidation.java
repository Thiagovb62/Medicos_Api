package com.example.demo.Domain.Consultas.Validations;

import com.example.demo.Domain.Consultas.DadosCadastroAgendamentoConsulta;
import com.example.demo.Repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InativeValidation {


    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DadosCadastroAgendamentoConsulta dados) {
        var paciente = pacienteRepository.findAtivoById(dados.idPaciente());
        if (!paciente) {
            throw new RuntimeException("Paciente inativo");
        }
    }
}
