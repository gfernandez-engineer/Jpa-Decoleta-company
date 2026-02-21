package com.demo.sunat.repository;

import com.demo.sunat.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByRucConsultadoOrderByCreatedAtDesc(String rucConsultado);
}
