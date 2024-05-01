package com.example.demo.Domain.Medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medico")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column(name = "nome")
    String nome;

    @Column(name = "email")
    String email;

    @Column(name = "telefone")
    String telefone;

    @Column(name = "crm")
    String crm;

    Boolean ativo;

    @Enumerated(EnumType.STRING)
    Especialidade especialidade;

    @Embedded
     Endereco endereco;

    public Medico(DadosCadastroMedicos dadosCadastroMedicos) {
        this.ativo = true;
        this.nome = dadosCadastroMedicos.nome();
        this.email = dadosCadastroMedicos.email();
        this.telefone = dadosCadastroMedicos.telefone();
        this.crm = dadosCadastroMedicos.crm();
        this.especialidade = dadosCadastroMedicos.especialidade();
        this.endereco = new Endereco(dadosCadastroMedicos.endereco());
    }
}
