package com.example.demo.Domain.Consultas.Validations;

import com.example.demo.Domain.Consultas.DadosCadastroAgendamentoConsulta;
import com.example.demo.Repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorInativeValidation implements ValidadorAgendamentoDeConsultas {

        @Autowired
       private MedicoRepository medicoRepository;
        public void validar(DadosCadastroAgendamentoConsulta dados) {

            if(dados.idMedico() == null) {
                return;
            }

            var medico = medicoRepository.findAtivoById(dados.idMedico());

            if (!medico) {
                throw new RuntimeException("Médico inativo");
            }
        }
}
