package com.apuestas_api.apuestas_back.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apuestas_api.apuestas_back.models.ERole;
import com.apuestas_api.apuestas_back.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}