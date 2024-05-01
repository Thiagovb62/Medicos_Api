package com.example.demo.Domain.Medico;

import com.example.demo.Domain.Medico.Especialidade;
import com.example.demo.Domain.Medico.Medico;

public record DadosListagemMedicos(Long id, String nome, String email, String crm, Boolean ativo, Especialidade especialidade) {

    public DadosListagemMedicos(Medico medico){
    this(medico.getId(), medico.getNome(),medico.getEmail(),medico.getCrm(),medico.getAtivo(),medico.getEspecialidade());
    }
}
