package com.example.demo.Domain.Consultas;

import com.example.demo.Domain.Medico.Especialidade;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(

        long id,

        Long idMedico,

        Long idPaciente,

        LocalDateTime data,

        Especialidade especialidade

) {
    public DadosDetalhamentoConsulta(Consultas consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData(), consulta.getMedico().getEspecialidade());
    }

}

