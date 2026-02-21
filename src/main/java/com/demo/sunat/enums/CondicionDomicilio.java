package com.demo.sunat.enums;

public enum CondicionDomicilio {
    HABIDO,
    NO_HABIDO,
    PENDIENTE,
    DESCONOCIDO;

    public static CondicionDomicilio fromString(String value) {
        if (value == null) return DESCONOCIDO;
        try {
            return valueOf(value.toUpperCase().trim().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            return DESCONOCIDO;
        }
    }
}
