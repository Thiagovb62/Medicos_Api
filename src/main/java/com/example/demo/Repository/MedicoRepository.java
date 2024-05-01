package com.example.demo.Repository;

import com.example.demo.Domain.Medico.Especialidade;
import com.example.demo.Domain.Medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    @Query("SELECT m FROM Medico m WHERE m.ativo = true")
    Page<Medico> findAllActive(Pageable pageable);

    @Query("SELECT m.ativo FROM Medico m WHERE m.id = :id")
    Boolean findAtivoById  (Long id);


    @Query("""
               SELECT m FROM Medico m WHERE m.ativo = true AND m.especialidade = :especialidade
                and m.id not in (
                SELECT c.medico.id FROM Consultas c WHERE c.data = :data
                )
                order by rand() limit 1""")
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);
}
