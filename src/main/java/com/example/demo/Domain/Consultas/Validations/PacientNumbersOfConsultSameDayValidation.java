package com.example.demo.Domain.Consultas.Validations;

import com.example.demo.Domain.Consultas.DadosCadastroAgendamentoConsulta;
import com.example.demo.Repository.ConsultasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacientNumbersOfConsultSameDayValidation implements ValidadorAgendamentoDeConsultas{

    @Autowired
    private ConsultasRepository consultasRepository;

    public void validar(DadosCadastroAgendamentoConsulta dados) {
      var beggingHour = dados.data().withHour(7);
        var endHour = dados.data().withHour(18);
        var consults = consultasRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(), beggingHour, endHour);
        if (consults) {
            throw new RuntimeException("Paciente atingiu o limite de consultas no mesmo dia");
        }
    }
}
