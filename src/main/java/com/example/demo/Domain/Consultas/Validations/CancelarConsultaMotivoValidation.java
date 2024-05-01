package com.example.demo.Domain.Consultas.Validations;

import com.example.demo.Domain.Consultas.DadosCadastroCancelamentoConsulta;
import org.springframework.stereotype.Component;

@Component
public class CancelarConsultaMotivoValidation implements ValidadorCncelamentoDeConsultas {

    public void validar(DadosCadastroCancelamentoConsulta dados) {
        String motivo = String.valueOf(dados.motivo());
        if (motivo.isEmpty() || motivo.isBlank()) {
            throw new IllegalArgumentException("Motivo não pode ser vazio");
        }
    }
}
