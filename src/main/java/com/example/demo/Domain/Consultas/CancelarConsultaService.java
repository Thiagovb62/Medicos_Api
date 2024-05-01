package com.example.demo.Domain.Consultas;

import com.example.demo.Domain.Consultas.Validations.ValidadorCncelamentoDeConsultas;
import com.example.demo.Repository.ConsultasCanceladasRepository;
import com.example.demo.Repository.ConsultasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancelarConsultaService {

    @Autowired
    private ConsultasCanceladasRepository consultasCanceladasRepository;

    @Autowired
    private ConsultasRepository consultasRepository;

    @Autowired
    private List<ValidadorCncelamentoDeConsultas> validadores;


    public DadosDetalhamentoCancelamentoConsulta cancelarConsulta(DadosCadastroCancelamentoConsulta dados) {
        ConsultasCanceladas consultasCanceladas = new ConsultasCanceladas();
        consultasCanceladas.setConsultas(consultasRepository.findById(dados.idConsulta()).get());
        consultasCanceladas.setMotivo(dados.motivo());
        validadores.forEach(validador -> validador.validar(dados));
        consultasCanceladasRepository.save(consultasCanceladas);
        consultasRepository.deleteById(dados.idConsulta());
        return new DadosDetalhamentoCancelamentoConsulta(consultasCanceladas);
    }
}
