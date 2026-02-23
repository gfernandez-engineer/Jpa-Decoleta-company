package com.demo.sunat.exception;

import com.demo.sunat.dto.BaseResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja violaciones de @Pattern / @Validated en el controller → 400 Bad Request
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(v -> v.getMessage())
                .findFirst()
                .orElse("Parámetro inválido");
        log.warn("Validación fallida: {}", message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse<>(4000, message, null));
    }

    // Maneja JSON malformado en el body → 400 Bad Request
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Object>> handleMalformedJson(HttpMessageNotReadableException ex) {
        log.warn("JSON malformado: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse<>(4000, "El cuerpo de la solicitud tiene un formato inválido", null));
    }

    // Maneja RUC con formato inválido → 400 Bad Request
    @ExceptionHandler(RucInvalidoException.class)
    public ResponseEntity<BaseResponse<Object>> handleRucInvalido(RucInvalidoException ex) {
        log.warn("RUC inválido: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse<>(4000, "RUC debe tener exactamente 11 dígitos", null));
    }

    // Maneja recurso no encontrado → 404 Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse<>(4004, ex.getMessage(), null));
    }

    // Maneja ruta inexistente → 404 Not Found
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleNoRouteFound(NoResourceFoundException ex) {
        log.warn("Ruta inexistente: {}", ex.getResourcePath());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse<>(4004, "La ruta solicitada no existe: " + ex.getResourcePath(), null));
    }

    // Maneja método HTTP incorrecto → 405 Method Not Allowed
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse<Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.warn("Método HTTP no soportado: {}", ex.getMethod());
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new BaseResponse<>(4005, "Método HTTP no permitido: " + ex.getMethod(), null));
    }

    // Maneja errores del proveedor Decolecta → devuelve el mismo status code del proveedor
    @ExceptionHandler(ProviderException.class)
    public ResponseEntity<BaseResponse<Object>> handleProviderException(ProviderException ex) {
        log.warn("Error del proveedor [{}]: {}", ex.getStatusCode(), ex.getProviderMessage());
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(new BaseResponse<>(ex.getStatusCode(), ex.getProviderMessage(), null));
    }

    // Captura cualquier otro error inesperado → 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleGenericException(Exception ex) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BaseResponse<>(5000, "Error interno del servidor", null));
    }
}
