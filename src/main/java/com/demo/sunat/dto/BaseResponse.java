package com.demo.sunat.dto;

import java.time.LocalDateTime;

public class BaseResponse<T> {

    private int codigo;
    private String mensaje;
    private T objeto;
    private LocalDateTime timestamp = LocalDateTime.now();

    public BaseResponse(int codigo, String mensaje, T objeto) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.objeto = objeto;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public T getObjeto() { return objeto; }
    public void setObjeto(T objeto) { this.objeto = objeto; }

    public LocalDateTime getTimestamp() { return timestamp; }
}
