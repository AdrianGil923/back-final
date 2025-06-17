package com.apuestas_api.apuestas_back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apuestas_api.apuestas_back.models.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByApuestaId(Long cancionId);

    void deleteByApuestaId(Long cancionId);
}

