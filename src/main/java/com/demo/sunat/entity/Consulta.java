package com.demo.sunat.entity;

import com.demo.sunat.enums.ResultadoConsulta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 11)
    private String rucConsultado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultadoConsulta resultado;

    @Column(length = 500)
    private String mensajeError;

    private Integer providerStatusCode;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "company_id", nullable = true)
    private Company company;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
