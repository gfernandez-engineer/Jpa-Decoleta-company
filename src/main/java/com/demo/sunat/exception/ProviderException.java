package com.demo.sunat.exception;

import lombok.Getter;

@Getter
public class ProviderException extends RuntimeException {

    private final int statusCode;
    private final String providerMessage;

    public ProviderException(int statusCode, String providerMessage) {
        super(providerMessage);
        this.statusCode = statusCode;
        this.providerMessage = providerMessage;
    }
}
