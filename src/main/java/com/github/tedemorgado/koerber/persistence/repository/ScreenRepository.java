package com.github.tedemorgado.koerber.persistence.repository;

import com.github.tedemorgado.koerber.persistence.model.ScreenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ScreenRepository extends JpaRepository<ScreenEntity, Long> {

   Optional<ScreenEntity> findByUuid(UUID uuid);
}
