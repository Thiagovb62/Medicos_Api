package com.example.demo.Controller;

import com.example.demo.Domain.Consultas.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private CancelarConsultaService cancelarConsultaService;
    private final AgendaDeConsultasService service;

    public ConsultaController(AgendaDeConsultasService service) {
        this.service = service;
    }

    @PostMapping("/agendar")
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosCadastroAgendamentoConsulta dados, UriComponentsBuilder uriBuilder) {
        System.out.println(dados);
        var consulta = service.agendarConsulta(dados);
        return ResponseEntity.ok(consulta);
    }

    @PostMapping("/cancelar")
    @Transactional
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity cancelar(@RequestBody @Valid DadosCadastroCancelamentoConsulta dados) {
        System.out.println(dados);
        var consulta = cancelarConsultaService.cancelarConsulta(dados);
        return ResponseEntity.ok(consulta);
    }

}