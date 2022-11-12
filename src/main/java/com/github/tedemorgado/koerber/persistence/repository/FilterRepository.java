package com.github.tedemorgado.koerber.persistence.repository;

import com.github.tedemorgado.koerber.persistence.model.FilterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FilterRepository extends JpaRepository<FilterEntity, Long> {

   List<FilterEntity> findAllByUuidOrderByVersionDesc(UUID uuid);

}
