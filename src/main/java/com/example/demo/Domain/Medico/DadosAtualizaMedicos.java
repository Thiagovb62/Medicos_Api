package com.example.demo.Domain.Medico;


public record DadosAtualizaMedicos(

        String nome,

        String email,

        String telefone,

        String crm,

        Especialidade especialidade,

        com.example.demo.Domain.Medico.DadosEndereco endereco


    ) {
}
