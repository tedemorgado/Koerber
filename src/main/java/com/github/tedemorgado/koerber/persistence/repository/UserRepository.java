package com.github.tedemorgado.koerber.persistence.repository;

import com.github.tedemorgado.koerber.persistence.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

   Optional<UserEntity> findByUuid(UUID uuid);
}
