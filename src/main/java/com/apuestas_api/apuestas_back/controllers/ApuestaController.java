package com.apuestas_api.apuestas_back.controllers;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apuestas_api.apuestas_back.models.Apuesta;
import com.apuestas_api.apuestas_back.models.User;
import com.apuestas_api.apuestas_back.repository.ApuestaReactionRepository;
import com.apuestas_api.apuestas_back.repository.ApuestaRepository;
import com.apuestas_api.apuestas_back.repository.ComentarioRepository;
import com.apuestas_api.apuestas_back.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/apuesta")

public class ApuestaController {

    @Autowired
    private ApuestaRepository apuestaRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public Page<Apuesta> getTweet(Pageable pageable) {
        return apuestaRepository.findAll(pageable);
    }

    @PostMapping("/create")
    public Apuesta createTweet(@Valid @RequestBody Apuesta apuesta) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        System.out.println("userid : " + userId);

        User user = getValidUser(userId);
        System.out.println("user");

        System.out.println(user);
        Apuesta myApuesta = new Apuesta(apuesta.getNombre(), apuesta.getDescripcion(), apuesta.getLink());
        myApuesta.setPostedBy(user);
        apuestaRepository.save(myApuesta);

        return myApuesta;
    }

    private User getValidUser(String userId) {
        Optional<User> userOpt = userRepository.findByUsername(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return userOpt.get();
    }

    @DeleteMapping("/borrar-todo")
    public ResponseEntity<String> borrarTodasLasApuestas() {
        apuestaRepository.deleteAll();
        return ResponseEntity.ok("Todas las apuestas han sido borradas.");
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Apuesta>> buscarApuestas(@RequestParam String termino) {
        List<Apuesta> resultados = apuestaRepository
                .findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(termino, termino);
        return ResponseEntity.ok(resultados);
    }

    @Autowired
    private ApuestaReactionRepository apuestaReactionRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> borrarApuesta(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado.");
        User user = userOpt.get();

        Optional<Apuesta> apuestaOpt = apuestaRepository.findById(id);
        if (apuestaOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apuesta no encontrada.");
        Apuesta apuesta = apuestaOpt.get();

        if (!apuesta.getPostedBy().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para borrar esta apuesta.");
        }

        
        comentarioRepository.deleteByApuestaId(apuesta.getId());
        apuestaReactionRepository.deleteByApuestaId(apuesta.getId());

        
        apuestaRepository.deleteById(id);

        return ResponseEntity.ok("Apuesta eliminada con éxito.");
    }

}