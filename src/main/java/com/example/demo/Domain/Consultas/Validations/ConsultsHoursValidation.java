package com.example.demo.Domain.Consultas.Validations;

import com.example.demo.Domain.Consultas.DadosCadastroAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ConsultsHoursValidation implements ValidadorAgendamentoDeConsultas{

    public void validar(DadosCadastroAgendamentoConsulta dados) {
        var data = dados.data();
        var now = LocalDateTime.now();
        var durationBetween = Duration.between(now, data).toMinutes();

        if (durationBetween < 30) {
            throw new RuntimeException("Horário para agendamento consulta deve ser com no minimo 30 minutos de antecedência");
        }
    }
}
