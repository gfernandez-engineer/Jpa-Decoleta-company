package com.demo.sunat.controller;

import com.demo.sunat.dto.BaseResponse;
import com.demo.sunat.dto.CompanyResponse;
import com.demo.sunat.dto.ConsultaResponse;
import com.demo.sunat.service.SunatService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sunat/ruc")
@RequiredArgsConstructor
@Validated
public class SunatController {

    private final SunatService sunatService;

    // GET /api/sunat/ruc/{ruc}
    // Consulta el RUC en el proveedor, guarda en BD y retorna los datos
    @GetMapping("/{ruc}")
    public ResponseEntity<BaseResponse<CompanyResponse>> consultarRuc(
            @PathVariable @Pattern(regexp = "\\d{11}", message = "RUC debe tener exactamente 11 dígitos") String ruc) {
        CompanyResponse response = sunatService.consultarRuc(ruc);
        return ResponseEntity.ok(new BaseResponse<>(2000, "Consulta realizada correctamente", response));
    }

    // GET /api/sunat/ruc/{ruc}/consultas
    // Retorna el historial de consultas para un RUC ordenado de más reciente a más antiguo
    @GetMapping("/{ruc}/consultas")
    public ResponseEntity<BaseResponse<List<ConsultaResponse>>> obtenerHistorial(
            @PathVariable @Pattern(regexp = "\\d{11}", message = "RUC debe tener exactamente 11 dígitos") String ruc) {
        List<ConsultaResponse> historial = sunatService.obtenerHistorial(ruc);
        return ResponseEntity.ok(new BaseResponse<>(2000, "Historial obtenido correctamente", historial));
    }
}
