package com.example.demo.Domain.Consultas.Validations;

import com.example.demo.Domain.Consultas.DadosCadastroAgendamentoConsulta;
import org.springframework.stereotype.Component;

@Component
public class HorarioValidation implements ValidadorAgendamentoDeConsultas  {

    public void validar(DadosCadastroAgendamentoConsulta dados) {
        var data = dados.data();

        var isThursday = data.getDayOfWeek().getValue() == 4;
        var beforeStarterHour = data.getHour() < 7;
        var afterFinalHour = data.getHour() > 18;
        if(isThursday  || beforeStarterHour || afterFinalHour) {
            throw new RuntimeException("Horário inválido para agendamento de consultas ");
        }
    }

}
