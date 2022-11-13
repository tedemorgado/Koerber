package com.github.tedemorgado.koerber.service;

import com.github.tedemorgado.koerber.controller.model.CreateFilterBranch;
import com.github.tedemorgado.koerber.controller.model.Filter;
import com.github.tedemorgado.koerber.controller.model.FilterBranch;
import com.github.tedemorgado.koerber.exception.BadRequestException;
import com.github.tedemorgado.koerber.exception.EntityNotFoundException;
import com.github.tedemorgado.koerber.persistence.model.BranchEntity;
import com.github.tedemorgado.koerber.persistence.model.FilterEntity;
import com.github.tedemorgado.koerber.persistence.model.ScreenEntity;
import com.github.tedemorgado.koerber.persistence.repository.BranchRepository;
import com.github.tedemorgado.koerber.persistence.repository.FilterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BranchService {

   private final BranchRepository branchRepository;
   private final FilterRepository filterRepository;

   public BranchService(final BranchRepository branchRepository, final FilterRepository filterRepository) {
      this.branchRepository = branchRepository;
      this.filterRepository = filterRepository;
   }

   @Transactional
   public FilterBranch createFilterBranch(final CreateFilterBranch createFilterBranch) {
      final List<BranchEntity> branchEntities = this.branchRepository.findByOriginalFilter_Uuid(createFilterBranch.getFilterId());
      if (!branchEntities.isEmpty()) {
         throw new BadRequestException("Creating branch from another branch is not acceptable");
      }

      final FilterEntity filterEntity = this.getFilterEntity(createFilterBranch.getFilterId());
      final FilterEntity newFilterEntity = this.filterRepository.save(this.cloneFilterEntity(filterEntity));

      final BranchEntity branchEntity = new BranchEntity();
      branchEntity.setUuid(UUID.randomUUID());
      branchEntity.setOriginalFilter(filterEntity);
      branchEntity.setOriginalFilterVersion(filterEntity.getVersion());
      branchEntity.setFilter(newFilterEntity);

      return Optional.of(this.branchRepository.save(branchEntity))
         .map(this::mapBranchEntityToFilterBranch)
         .get();
   }

   /*
• Update a filter on a branch
• Soft delete a branch (Optional)
• List all branches of a filter (Optional)
• Merge a branch with a filter.
    */
   private FilterEntity getFilterEntity(final UUID filterId) {
      return this.filterRepository.findAllByUuidOrderByVersionDesc(filterId)
         .stream()
         .findFirst()
         .orElseThrow(() -> new EntityNotFoundException("Filter not found for id " + filterId));
   }

   private FilterEntity cloneFilterEntity(final FilterEntity filter) {
      final FilterEntity filterEntity = new FilterEntity();
      filterEntity.setVersion(1L);
      filterEntity.setUuid(UUID.randomUUID());
      filterEntity.setOutputFilter(filter.getOutputFilter());
      filterEntity.setData(filter.getData());
      filterEntity.setName(filter.getName());
      filterEntity.setScreen(filter.getScreen());
      filterEntity.setUser(filter.getUser());
      return filterEntity;
   }

   private FilterBranch mapBranchEntityToFilterBranch(final BranchEntity branchEntity) {
      final FilterBranch filterBranch = new FilterBranch();
      filterBranch.setFilter(this.mapFilterEntityToFilter(branchEntity.getFilter()));
      filterBranch.setId(branchEntity.getUuid());
      return filterBranch;
   }

   private Filter mapFilterEntityToFilter(final FilterEntity filterEntity) {
      final UUID screenId = Optional.ofNullable(filterEntity.getScreen())
         .map(ScreenEntity::getUuid)
         .orElse(null);

      return new Filter(filterEntity.getUuid(), filterEntity.getUser().getUuid(), filterEntity.getName(), filterEntity.getData(), filterEntity.getOutputFilter(), screenId, filterEntity.getVersion());
   }
}
