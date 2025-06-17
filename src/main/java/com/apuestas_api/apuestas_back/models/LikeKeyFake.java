package com.apuestas_api.apuestas_back.models;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
class ApuestaReactionKey implements Serializable {

    @Column(name = "reaction_id")
    Long reactionId;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "apuesta_id")
    Long apuestaId;

    public Long getReactionId() {
        return reactionId;
    }

    public void setReactionId(Long reactionId) {
        this.reactionId = reactionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getApuestaId() {
        return apuestaId;
    }

    public void setApuestaId(Long apuestaId) {
        this.apuestaId = apuestaId;
    }
}
