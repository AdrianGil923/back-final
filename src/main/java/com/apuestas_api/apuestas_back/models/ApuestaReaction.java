package com.apuestas_api.apuestas_back.models;

import jakarta.persistence.*;

@Entity
@Table(name = "apuesta_reactions", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "apuesta_id" }),
})
public class ApuestaReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reaction_id")
    Long reactionId;

    public Long getReactionId() {
        return reactionId;
    }

    public void setReactionId(Long reactionId) {
        this.reactionId = reactionId;
    }

    @Column(name = "user_id")
    Long userId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "Apuesta_id")
    Long apuestaId;

    public Long getApuesta_Id() {
        return apuestaId;
    }

    public void setApuesta_Id(Long apuesta_Id) {
        this.apuestaId = apuesta_Id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.userId = user.getId();
        this.user = user;
    }

    
    @ManyToOne
    @MapsId("apuestaId")
    @JoinColumn(name = "apuesta_id")
    private Apuesta apuesta;

    public Apuesta getApuesta() {
        return apuesta;
    }

    public void setApuesta(Apuesta apuesta) {
        this.apuestaId = apuesta.getId();
        this.apuesta = apuesta;
    }

    @ManyToOne
    @MapsId("reactionId")
    @JoinColumn(name = "reaction_id")
    private Reaction reaction;

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reactionId = reaction.getId();
        this.reaction = reaction;
    }
}
