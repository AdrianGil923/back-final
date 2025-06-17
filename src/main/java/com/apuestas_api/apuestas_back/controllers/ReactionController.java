package com.apuestas_api.apuestas_back.controllers;


import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.apuestas_api.apuestas_back.models.ApuestaReaction;
import com.apuestas_api.apuestas_back.models.Reaction;
import com.apuestas_api.apuestas_back.models.User;
import com.apuestas_api.apuestas_back.payload.request.ApuestaReactionRequest;
import com.apuestas_api.apuestas_back.payload.response.ReactionCountResponse;
import com.apuestas_api.apuestas_back.repository.ApuestaReactionRepository;
import com.apuestas_api.apuestas_back.repository.ApuestaRepository;
import com.apuestas_api.apuestas_back.repository.ReactionRepository;
import com.apuestas_api.apuestas_back.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reactions")

public class ReactionController {

    @Autowired
    private ApuestaReactionRepository apuestaReactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApuestaRepository apuestaRepository;
    @Autowired
    private ReactionRepository reactionRepository;

    @GetMapping("/all")
    public Page<ApuestaReaction> getReaction(Pageable pageable) {
        return apuestaReactionRepository.findAll(pageable);
    }

    @DeleteMapping("/borrar-todo")
    public ResponseEntity<String> borrarTodasLasApuestasYReacciones() {
        // Borra todas las reacciones primero
        apuestaReactionRepository.deleteAll();

        // Luego borra todas las canciones
        apuestaRepository.deleteAll();

        return ResponseEntity.ok("Todas las apuestas y sus reacciones han sido borradas.");
    }

    @GetMapping("/most-voted/{cancionId}")
    public ResponseEntity<String> getMostVotedReaction(@PathVariable Long apuestaId) {
        List<Object[]> results = apuestaReactionRepository.findMostVotedReactionByApuestaId(apuestaId);

        if (results.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String mostVotedReaction = results.get(0)[0].toString(); // EReaction name
        return ResponseEntity.ok(mostVotedReaction);
    }

    @PostMapping("/create")
    public ApuestaReaction createReaction(@Valid @RequestBody ApuestaReactionRequest apuestaReaction) {
        System.out.println("apuestaid : " + apuestaReaction.getApuestaId());
        System.out.println("reactiontid : " + apuestaReaction.getReactionId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        System.out.println("userid : " + userId);

        User user = getValidUser(userId);
        System.out.println("user");

        System.out.println(user);

        ApuestaReaction myApuestaReaction = new ApuestaReaction();
        Apuesta myApuesta = getValidApuesta(apuestaReaction.getApuestaId());
        System.out.println("object apuesta : ");
        System.out.println(myApuesta);

        Reaction myReaction = getValidReaction(apuestaReaction.getReactionId());
        System.out.println("object reaction : ");
        System.out.println(myReaction);

        // myTweetReaction.setUserId(user.getId());
        // myTweetReaction.setTweetId(myTweet.getId());
        // myTweetReaction.setReactionId(myReaction.getId());

        myApuestaReaction.setUser(user);
        myApuestaReaction.setApuesta(myApuesta);
        myApuestaReaction.setReaction(myReaction);

        System.out.println("apuesta reaction : ");
        System.out.println(myApuestaReaction.getReactionId());
        System.out.println(myApuestaReaction.getApuesta_Id());

        System.out.println(myApuestaReaction.getUserId());

        apuestaReactionRepository.save(myApuestaReaction);

        return myApuestaReaction;
    }

    private User getValidUser(String userId) {
        Optional<User> userOpt = userRepository.findByUsername(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return userOpt.get();
    }

    private Apuesta getValidApuesta(Long apuestaId) {
        Optional<Apuesta> apuestaOpt = apuestaRepository.findById(apuestaId);
        if (!apuestaOpt.isPresent()) {
            throw new RuntimeException("Tweet not found");
        }
        return apuestaOpt.get();
    }

    private Reaction getValidReaction(Long reactionId) {
        Optional<Reaction> reactionOpt = reactionRepository.findById(reactionId);
        if (!reactionOpt.isPresent()) {
            throw new RuntimeException("Reaction not found");
        }
        return reactionOpt.get();
    }

}