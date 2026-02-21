package com.demo.sunat.enums;

public enum EstadoContribuyente {
    ACTIVO,
    BAJA,
    SUSPENDIDO,
    DESCONOCIDO;

    public static EstadoContribuyente fromString(String value) {
        if (value == null) return DESCONOCIDO;
        try {
            return valueOf(value.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            return DESCONOCIDO;
        }
    }
}
