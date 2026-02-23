package com.demo.sunat.exception;

import com.demo.sunat.dto.ProviderErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        String providerMessage = extractMessage(response);

        if (status == 401) {
            return new ProviderException(status, "Token inválido o expirado. Verifique DECOLECTA_TOKEN.");
        }
        if (status >= 500) {
            return new ProviderException(status, "Error interno del proveedor Decolecta (HTTP " + status + ").");
        }
        // 400, 404 u otros 4xx
        return new ProviderException(status, providerMessage);
    }

    private String extractMessage(Response response) {
        if (response.body() == null) {
            return "Error del proveedor sin detalle (HTTP " + response.status() + ")";
        }
        try (InputStream stream = response.body().asInputStream()) {
            String body = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            ProviderErrorResponse error = objectMapper.readValue(body, ProviderErrorResponse.class);
            return error.message() != null ? error.message() : body;
        } catch (IOException e) {
            return "Error del proveedor sin detalle (HTTP " + response.status() + ")";
        }
    }
}
