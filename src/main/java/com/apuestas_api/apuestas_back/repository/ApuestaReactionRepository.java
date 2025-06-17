package com.apuestas_api.apuestas_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apuestas_api.apuestas_back.models.ApuestaReaction;
import com.apuestas_api.apuestas_back.payload.response.ReactionCountResponse;

@Repository
public interface ApuestaReactionRepository extends JpaRepository<ApuestaReaction, Long> {
    @Query("SELECT cr.reaction.description, COUNT(cr) as total " +
            "FROM ApuestaReaction cr " +
            "WHERE cr.apuesta.id = :apuestaId " +
            "GROUP BY cr.reaction.description " +
            "ORDER BY total DESC")
    List<Object[]> findMostVotedReactionByApuestaId(@Param("apuestaId") Long apuestaId);

    void deleteByApuestaId(Long apuestaId);

}
