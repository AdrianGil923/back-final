package com.apuestas_api.apuestas_back.payload.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ComentarioRequest {

    @NotNull
    private Long apuestaId;  

    @NotBlank
    private String contenido;  

    public Long getApuestaId() {
        return apuestaId;
    }

    public void setApuestaId(Long apuestaId) {
        this.apuestaId = apuestaId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
