package com.apuestas_api.apuestas_back.payload.request;

import jakarta.validation.constraints.NotBlank;

public class ApuestaReactionRequest {
    private Long apuestaId;  

    public Long getApuestaId() {
        return apuestaId;
    }

    public void setApuestaId(Long apuestaId) {
        this.apuestaId = apuestaId;
    }

    private Long reactionId;  

    public Long getReactionId() {
        return reactionId;
    }

    public void setReactionId(Long reactionId) {
        this.reactionId = reactionId;
    }
}
