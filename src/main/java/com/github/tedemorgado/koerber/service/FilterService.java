package com.github.tedemorgado.koerber.service;

import com.github.tedemorgado.koerber.controller.model.Filter;
import com.github.tedemorgado.koerber.exception.EntityNotFoundException;
import com.github.tedemorgado.koerber.persistence.model.FilterEntity;
import com.github.tedemorgado.koerber.persistence.model.ScreenEntity;
import com.github.tedemorgado.koerber.persistence.repository.FilterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


   @Transactional(readOnly = true)
   public Page<Filter> getAllFilters(final Pageable pageable) {
      return null;
      /*return this.filterRepository
         .distinct(pageable)
         .map(this::mapFilterEntityToFilter);*/
   }

   @Transactional(readOnly = true)
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
         .map(this::mapFilterEntityToFilter)
         .orElseThrow(() -> new EntityNotFoundException("No filter found with id " + uuid));
   }

   private Filter mapFilterEntityToFilter(final FilterEntity filterEntity) {
      final UUID screenId = Optional.ofNullable(filterEntity.getScreen())
         .map(ScreenEntity::getUuid)
         .orElse(null);

      return new Filter(filterEntity.getUuid(), filterEntity.getUser().getUuid(), filterEntity.getName(), filterEntity.getData(), filterEntity.getOutputFilter(), screenId, filterEntity.getVersion());
   }
}
