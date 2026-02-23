package com.demo.sunat.exception;

public class RucInvalidoException extends RuntimeException {

    public RucInvalidoException(String ruc) {
        super("RUC debe tener exactamente 11 dígitos: " + ruc);
    }
}
