package com.apuestas_api.apuestas_back.models;
import jakarta.persistence.*;

@Entity
@Table(name = "reactions")
public class Reaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private EReaction description;

  public Reaction() {
  }

  public Reaction(EReaction description) {
    this.description = description;
  }

  

  public EReaction getDescription() {
    return description;
  }

  public void setDescription(EReaction description) {
    this.description = description;
  }

}