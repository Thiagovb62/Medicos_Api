package com.example.demo.Domain.Consultas;

public record DadosDetalhamentoCancelamentoConsulta(

        long id,

        Long idConsulta,
        Motivo motivo

) {
    public DadosDetalhamentoCancelamentoConsulta(ConsultasCanceladas consulta) {
        this(consulta.getId(), consulta.getConsultas().getId(), consulta.getMotivo());
    }
}

