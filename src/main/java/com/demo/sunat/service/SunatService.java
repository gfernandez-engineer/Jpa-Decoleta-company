package com.demo.sunat.service;

import com.demo.sunat.client.DecolectaClient;
import com.demo.sunat.dto.CompanyResponse;
import com.demo.sunat.dto.ConsultaResponse;
import com.demo.sunat.dto.SunatRucResponse;
import com.demo.sunat.entity.Company;
import com.demo.sunat.entity.Consulta;
import com.demo.sunat.enums.CondicionDomicilio;
import com.demo.sunat.enums.EstadoContribuyente;
import com.demo.sunat.enums.ResultadoConsulta;
import com.demo.sunat.exception.ProviderException;
import com.demo.sunat.exception.ResourceNotFoundException;
import com.demo.sunat.mapper.CompanyMapper;
import com.demo.sunat.repository.CompanyRepository;
import com.demo.sunat.repository.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SunatService {

    private final DecolectaClient decolectaClient;
    private final CompanyRepository companyRepository;
    private final ConsultaRepository consultaRepository;
    private final CompanyMapper mapper;

    @Transactional
    public CompanyResponse consultarRuc(String ruc) {

        // 1. Buscar si la empresa ya existe en la BD
        Company company = companyRepository.findByRuc(ruc).orElse(null);

        // 3. Cache: si existe y fue actualizada hace menos de 10 minutos, no llamamos al proveedor
        if (company != null && company.getUpdatedAt().isAfter(LocalDateTime.now().minusMinutes(10))) {
            Consulta consultaCache = new Consulta();
            consultaCache.setRucConsultado(ruc);
            consultaCache.setResultado(ResultadoConsulta.SUCCESS);
            consultaCache.setMensajeError("cache");
            consultaCache.setCompany(company);
            consultaRepository.save(consultaCache);

            List<Consulta> historial = consultaRepository.findByRucConsultadoOrderByCreatedAtDesc(ruc);
            return mapper.toCompanyResponse(company, historial);
        }

        // 4. Llamar al proveedor Decolecta via Feign
        try {
            SunatRucResponse response = decolectaClient.consultarRuc(ruc);

            // 5. Si company no existe, crear una nueva; si ya existe, actualizarla
            if (company == null) {
                company = new Company();
            }
            company.setRuc(response.numeroDocumento());
            company.setRazonSocial(response.razonSocial());
            company.setEstado(EstadoContribuyente.fromString(response.estado()));
            company.setCondicion(CondicionDomicilio.fromString(response.condicion()));
            company.setDireccion(response.direccion());
            company.setUbigeo(response.ubigeo());
            company.setDepartamento(response.departamento());
            company.setProvincia(response.provincia());
            company.setDistrito(response.distrito());
            company.setEsAgenteRetencion(Boolean.TRUE.equals(response.esAgenteRetencion()));
            company.setEsBuenContribuyente(Boolean.TRUE.equals(response.esBuenContribuyente()));
            companyRepository.save(company);

            // 6. Registrar la consulta como exitosa
            Consulta consulta = new Consulta();
            consulta.setRucConsultado(ruc);
            consulta.setResultado(ResultadoConsulta.SUCCESS);
            consulta.setCompany(company);
            consultaRepository.save(consulta);

            List<Consulta> historial = consultaRepository.findByRucConsultadoOrderByCreatedAtDesc(ruc);
            return mapper.toCompanyResponse(company, historial);

        } catch (ProviderException ex) {
            // 7. Si el proveedor falla, registrar la consulta como error
            Consulta consulta = new Consulta();
            consulta.setRucConsultado(ruc);
            consulta.setResultado(ResultadoConsulta.ERROR);
            consulta.setMensajeError(ex.getProviderMessage());
            consulta.setProviderStatusCode(ex.getStatusCode());
            consultaRepository.save(consulta);
            throw ex;
        }
    }

    @Transactional(readOnly = true)
    public List<ConsultaResponse> obtenerHistorial(String ruc) {
        List<Consulta> consultas = consultaRepository.findByRucConsultadoOrderByCreatedAtDesc(ruc);
        if (consultas.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron consultas para el RUC: " + ruc);
        }
        List<ConsultaResponse> resultado = new ArrayList<>();
        for (Consulta c : consultas) {
            resultado.add(mapper.toConsultaResponse(c));
        }
        return resultado;
    }
}
