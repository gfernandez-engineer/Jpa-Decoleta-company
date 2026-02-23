package com.demo.sunat.entity;

import com.demo.sunat.enums.CondicionDomicilio;
import com.demo.sunat.enums.EstadoContribuyente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 11)
    private String ruc;

    @Column(nullable = false)
    private String razonSocial;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoContribuyente estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CondicionDomicilio condicion;

    private String direccion;
    private String ubigeo;
    private String departamento;
    private String provincia;
    private String distrito;

    private boolean esAgenteRetencion;
    private boolean esBuenContribuyente;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
