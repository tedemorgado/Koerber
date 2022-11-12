package com.github.tedemorgado.koerber.service;

import com.github.tedemorgado.koerber.controller.model.Filter;
import com.github.tedemorgado.koerber.exception.EntityNotFoundException;
import com.github.tedemorgado.koerber.persistence.model.FilterEntity;
import com.github.tedemorgado.koerber.persistence.model.ScreenEntity;
import com.github.tedemorgado.koerber.persistence.repository.FilterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FilterService {

   private final FilterRepository filterRepository;

   public FilterService(final FilterRepository filterRepository) {
      this.filterRepository = filterRepository;
   }

   /*
   • Create filter
   • Update filter
   • Soft delete filter
   • List all filter (latest version) (Optional)
    */

   public Set<Long> getFilterVersions(final UUID uuid) {
      final Set<Long> versions = this.filterRepository.findAllByUuidOrderByVersionDesc(uuid)
         .stream()
         .map(FilterEntity::getVersion)
         .sorted(Comparator.reverseOrder())
         .collect(Collectors.toCollection(LinkedHashSet::new));

      if (versions.isEmpty()) {
         throw new EntityNotFoundException("No filter found with id " + uuid);
      }

      return versions;
   }

   @Transactional(readOnly = true)
   public Filter getFilterById(final UUID uuid, final Long version) {
      final List<FilterEntity> filterEntities = this.filterRepository.findAllByUuidOrderByVersionDesc(uuid);

      final Optional<FilterEntity> filterEntity;
      if (version != null) {
         filterEntity = filterEntities.stream().filter(fe -> fe.getVersion().equals(version)).findFirst();
      } else {
         filterEntity = filterEntities
            .stream()
            .findFirst();
      }

      return filterEntity
         .map(fe -> {
            final UUID screenId = Optional.ofNullable(fe.getScreen())
               .map(ScreenEntity::getUuid)
               .orElse(null);
            return new Filter(fe.getUuid(), fe.getUser().getUuid(), fe.getName(), fe.getData(), fe.getOutputFilter(), screenId, fe.getVersion());
         })
         .orElseThrow(() -> new EntityNotFoundException("No filter found with id " + uuid));
   }
}
