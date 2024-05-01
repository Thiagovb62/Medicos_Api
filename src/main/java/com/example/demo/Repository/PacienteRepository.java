package com.example.demo.Repository;

import com.example.demo.Domain.Paciente.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);



    @Query("SELECT p FROM Paciente p WHERE p.ativo = true AND p.id = :id")
    Boolean findAtivoById (Long id);

}