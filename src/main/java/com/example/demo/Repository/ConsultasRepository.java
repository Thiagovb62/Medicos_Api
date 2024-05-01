package com.example.demo.Repository;

import com.example.demo.Domain.Consultas.Consultas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultasRepository extends JpaRepository<Consultas, Long>{


    Boolean existsByMedicoIdAndData(Long id, LocalDateTime data);


    Boolean existsByPacienteIdAndDataBetween(Long aLong, LocalDateTime beggingHour, LocalDateTime endHour);
}
