package com.demo.sunat.dto;

import com.demo.sunat.enums.CondicionDomicilio;
import com.demo.sunat.enums.EstadoContribuyente;

import java.time.LocalDateTime;
import java.util.List;

public record CompanyResponse(
        Long id,
        String ruc,
        String razonSocial,
        EstadoContribuyente estado,
        CondicionDomicilio condicion,
        String direccion,
        String ubigeo,
        String departamento,
        String provincia,
        String distrito,
        boolean esAgenteRetencion,
        boolean esBuenContribuyente,
        LocalDateTime createdAt,
        List<ConsultaResponse> consultas
) {}
