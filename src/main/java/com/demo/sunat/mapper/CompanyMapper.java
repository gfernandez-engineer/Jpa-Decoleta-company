package com.demo.sunat.mapper;

import com.demo.sunat.dto.CompanyResponse;
import com.demo.sunat.dto.ConsultaResponse;
import com.demo.sunat.dto.SunatRucResponse;
import com.demo.sunat.entity.Company;
import com.demo.sunat.entity.Consulta;
import com.demo.sunat.enums.CondicionDomicilio;
import com.demo.sunat.enums.EstadoContribuyente;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyMapper {

    // Convierte la respuesta del proveedor en una entidad Company
    public Company toEntity(SunatRucResponse response) {
        Company company = new Company();
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
        return company;
    }

    // Convierte una entidad Consulta en su DTO de respuesta
    public ConsultaResponse toConsultaResponse(Consulta consulta) {
        return new ConsultaResponse(
                consulta.getId(),
                consulta.getRucConsultado(),
                consulta.getResultado(),
                consulta.getMensajeError(),
                consulta.getProviderStatusCode(),
                consulta.getCreatedAt()
        );
    }

    // Convierte una entidad Company + lista de consultas en su DTO de respuesta
    public CompanyResponse toCompanyResponse(Company company, List<Consulta> consultas) {
        List<ConsultaResponse> lista = new ArrayList<>();
        for (Consulta c : consultas) {
            lista.add(toConsultaResponse(c));
        }

        return new CompanyResponse(
                company.getId(),
                company.getRuc(),
                company.getRazonSocial(),
                company.getEstado(),
                company.getCondicion(),
                company.getDireccion(),
                company.getUbigeo(),
                company.getDepartamento(),
                company.getProvincia(),
                company.getDistrito(),
                company.isEsAgenteRetencion(),
                company.isEsBuenContribuyente(),
                company.getCreatedAt(),
                lista
        );
    }
}
