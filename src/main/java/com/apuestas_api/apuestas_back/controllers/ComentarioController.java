package com.apuestas_api.apuestas_back.controllers;


import com.apuestas_api.apuestas_back.models.Apuesta;
import com.apuestas_api.apuestas_back.models.Comentario;
import com.apuestas_api.apuestas_back.models.User;
import com.apuestas_api.apuestas_back.payload.request.ComentarioRequest;
import com.apuestas_api.apuestas_back.payload.response.ComentarioResponse;
import com.apuestas_api.apuestas_back.repository.ApuestaRepository;
import com.apuestas_api.apuestas_back.repository.ComentarioRepository;
import com.apuestas_api.apuestas_back.repository.UserRepository;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ApuestaRepository cancionRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public Page<Comentario> getAllComentarios(Pageable pageable) {
        return comentarioRepository.findAll(pageable);
    }

    @GetMapping("/get/{apuestaId}")
    public ResponseEntity<List<ComentarioResponse>> getComentariosPorApuesta(@PathVariable Long apuestaId) {
        List<Comentario> comentarios = comentarioRepository.findByApuestaId(apuestaId);

        List<ComentarioResponse> respuesta = comentarios.stream().map(c -> new ComentarioResponse(
                c.getId(),
                c.getContenido(),
                c.getUsuario().getUsername() // O el campo que uses como nombre
        )).toList();

        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/create")
    public Comentario createComentario(@Valid @RequestBody ComentarioRequest comentarioRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = getValidUser(username);
        Apuesta apuesta = getValidApuesta(comentarioRequest.getApuestaId());

        Comentario comentario = new Comentario();
        comentario.setContenido(comentarioRequest.getContenido());
        comentario.setApuesta(apuesta);
        comentario.setUsuario(user);

        return comentarioRepository.save(comentario);
    }

    private User getValidUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Apuesta getValidApuesta(Long apuestaId) {
        return cancionRepository.findById(apuestaId)
                .orElseThrow(() -> new RuntimeException("Canción no encontrada"));
    }
}