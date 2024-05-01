package com.example.demo.Domain.Consultas.Validations;

import com.example.demo.Domain.Consultas.DadosCadastroAgendamentoConsulta;
import com.example.demo.Repository.ConsultasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorBusyScheduleValidation implements ValidadorAgendamentoDeConsultas{

    @Autowired
    private ConsultasRepository consultasRepository;

public void validar(DadosCadastroAgendamentoConsulta dados) {
        var medicoOcupado = consultasRepository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if (medicoOcupado) {
            throw new RuntimeException("Médico ocupado neste horário");
        }
    }

}
