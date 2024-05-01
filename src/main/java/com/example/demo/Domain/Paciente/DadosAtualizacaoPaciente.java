package com.example.demo.Domain.Paciente;

import com.example.demo.Domain.Medico.DadosEndereco;
import jakarta.validation.constraints.NotNull;


public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
