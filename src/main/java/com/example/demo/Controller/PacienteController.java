package com.example.demo.Controller;


import com.example.demo.Domain.Paciente.DadosCadastroPaciente;
import com.example.demo.Domain.Paciente.DadosListagemPaciente;
import com.example.demo.Domain.Paciente.Paciente;
import com.example.demo.Repository.PacienteRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dados) {
        repository.save(new Paciente(dados));
    }

    @GetMapping
    public Page<DadosListagemPaciente> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
    }

//    @PutMapping
//    @Transactional
//    public void atualizar(@RequestBody @Valid med.voll.api.domain.paciente.DadosAtualizacaoPaciente dados) {
//        var paciente = repository.getReferenceById(dados.id());
//        if (dados.nome() != null) paciente.setNome(dados.nome());
//        if (dados.telefone() != null) paciente.setTelefone(dados.telefone());
//        if (dados.endereco() != null)  paciente.getEndereco().atualiarInformacoes(dados.endereco());
//    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.excluir();
    }


}