package com.demo.sunat.dto;

import com.demo.sunat.enums.ResultadoConsulta;

import java.time.LocalDateTime;

public record ConsultaResponse(
        Long id,
        String rucConsultado,
        ResultadoConsulta resultado,
        String mensajeError,
        Integer providerStatusCode,
        LocalDateTime createdAt
) {}
