package com.demo.sunat.entity;

import com.demo.sunat.enums.CondicionDomicilio;
import com.demo.sunat.enums.EstadoContribuyente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@Data
@Builder
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

    @Column(nullable = false)
    private boolean esAgenteRetencion;

    @Column(nullable = false)
    private boolean esBuenContribuyente;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Consulta> consultas = new ArrayList<>();

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
