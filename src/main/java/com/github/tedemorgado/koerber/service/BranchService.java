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
      this.branchRepository.findByFilter_Uuid(createFilterBranch.getFilterId()).ifPresent(be -> {
         throw new BadRequestException("Creating branch from another branch is not acceptable");
      });

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

   @Transactional
   public void deleteFilterBranch(final UUID branchId) {
      final BranchEntity branchEntity = this.getBranchEntity(branchId);
      this.branchRepository.delete(branchEntity);
   }

   @Transactional
   public void mergeBranch(final UUID branchId) {
      final BranchEntity branchEntity = this.getBranchEntity(branchId);
      this.branchRepository.delete(branchEntity);

      final FilterEntity originalFilterEntity = branchEntity.getOriginalFilter();
      final FilterEntity newFilterEntity = branchEntity.getFilter();

      originalFilterEntity.setOutputFilter(newFilterEntity.getOutputFilter());
      originalFilterEntity.setData(newFilterEntity.getData());
      originalFilterEntity.setName(newFilterEntity.getName());
      originalFilterEntity.setScreen(newFilterEntity.getScreen());
      originalFilterEntity.setVersion(originalFilterEntity.getVersion() + 1);

      this.filterRepository.save(originalFilterEntity);
   }

   private FilterEntity getFilterEntity(final UUID filterId) {
      return this.filterRepository.findAllByUuidOrderByVersionDesc(filterId)
         .stream()
         .findFirst()
         .orElseThrow(() -> new EntityNotFoundException("Filter not found for id " + filterId));
   }

   private BranchEntity getBranchEntity(final UUID branchId) {
      return this.branchRepository.findByUuid(branchId)
         .orElseThrow(() -> new EntityNotFoundException("Branch not found for id " + branchId));
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
