package com.example.demo.Domain.Consultas.Validations;

import com.example.demo.Domain.Consultas.DadosCadastroCancelamentoConsulta;
import com.example.demo.Repository.ConsultasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class CancelarConsultaDataValidation implements ValidadorCncelamentoDeConsultas {

    @Autowired
    private ConsultasRepository consultasRepository;

    public void validar(DadosCadastroCancelamentoConsulta dados){
        consultasRepository.findById(dados.idConsulta()).ifPresent(consultas -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime consultaDataHora = consultas.getData();
            long hoursDifference = ChronoUnit.HOURS.between(now, consultaDataHora);
            System.out.println(hoursDifference);
            if(hoursDifference < 24){
                throw new RuntimeException("Não é possível cancelar a consulta com menos de 24 horas de antecedência.");
            }
        });
    }
}
