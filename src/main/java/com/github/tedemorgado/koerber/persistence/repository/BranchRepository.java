package com.github.tedemorgado.koerber.persistence.repository;

import com.github.tedemorgado.koerber.persistence.model.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BranchRepository extends JpaRepository<BranchEntity, Long> {
   Optional<BranchEntity> findByUuid(UUID uuid);

   List<BranchEntity> findByOriginalFilter_Uuid(UUID uuid);

   Optional<BranchEntity> findByOriginalFilter_Id(Long id);
}
