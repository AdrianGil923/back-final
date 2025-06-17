package com.apuestas_api.apuestas_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.apuestas_api.apuestas_back.models.Reaction;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

}