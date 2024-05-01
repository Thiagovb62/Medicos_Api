package com.example.demo.Controller;

import com.example.demo.Domain.Medico.*;
import com.example.demo.Repository.MedicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
@EnableMethodSecurity(securedEnabled = true)
public class MedicosController {


    @Autowired
    private MedicoRepository medicoRepository;


    @GetMapping("/lista")
    public ResponseEntity<Page<DadosListagemMedicos>> listaMedicos(@PageableDefault(size = 10,sort = {"nome"}) Pageable pageable) {
        return ResponseEntity.ok(medicoRepository.findAllActive(pageable).map(DadosListagemMedicos::new));
    }

    @GetMapping("/detalhes/{id}")
    public ResponseEntity<DadosDetalhamentoMedico> detalhesMedico(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id);
            return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }


    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> cadastrarMedico(@RequestBody @Valid DadosCadastroMedicos dadosCadastroMedicos, UriComponentsBuilder uriBuilder){
       Medico medico = new Medico(dadosCadastroMedicos);
            medicoRepository.save(medico);
           var uri =  uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
           return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));

    }


    @PutMapping("/atualiza/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> atualizarMedico(@PathVariable Long id, @RequestBody @Valid DadosAtualizaMedicos dadosCadastroMedicos){
        Medico medico = medicoRepository.getReferenceById(id);
       if (dadosCadastroMedicos.nome() != null) medico.setNome(dadosCadastroMedicos.nome());
       if (dadosCadastroMedicos.email() != null) medico.setEmail(dadosCadastroMedicos.email());
       if (dadosCadastroMedicos.telefone() != null) medico.setTelefone(dadosCadastroMedicos.telefone());
       if (dadosCadastroMedicos.crm() != null) medico.setCrm(dadosCadastroMedicos.crm());
       if (dadosCadastroMedicos.especialidade() != null) medico.setEspecialidade(dadosCadastroMedicos.especialidade());
      Medico medicoAtualiazado = medicoRepository.save(medico);
       return ResponseEntity.ok(new DadosDetalhamentoMedico(medicoAtualiazado));
    }

    @DeleteMapping("/deleta/{id}")
    @Secured("ROLE_ADMIN")
    @Transactional
    public ResponseEntity deletarMedico(@PathVariable Long id) throws  Exception{
       try {
            medicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
         }catch (Exception e){
              throw new Exception("Erro ao deletar medico");
       }
    }

    @DeleteMapping("/desativar/{id}")
    @Transactional
    public ResponseEntity<String> desativarMedico(@PathVariable Long id) throws  Exception{
        try {
            medicoRepository.findById(id).get().setAtivo(false);
            String jsonMessage = "{\"message\": \"Médico desativado com sucesso\"}";
            return ResponseEntity.ok(jsonMessage);

        }catch (Exception e){
            throw new Exception("Erro ao desativar medico");
        }
    }


}
