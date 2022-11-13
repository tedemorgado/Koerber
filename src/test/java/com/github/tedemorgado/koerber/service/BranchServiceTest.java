package com.github.tedemorgado.koerber.service;

import com.github.tedemorgado.koerber.controller.model.CreateFilterBranch;
import com.github.tedemorgado.koerber.controller.model.FilterBranch;
import com.github.tedemorgado.koerber.exception.BadRequestException;
import com.github.tedemorgado.koerber.exception.EntityNotFoundException;
import com.github.tedemorgado.koerber.persistence.model.BranchEntity;
import com.github.tedemorgado.koerber.persistence.model.FilterEntity;
import com.github.tedemorgado.koerber.persistence.model.UserEntity;
import com.github.tedemorgado.koerber.persistence.repository.BranchRepository;
import com.github.tedemorgado.koerber.persistence.repository.FilterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class BranchServiceTest {

   private BranchService underTest;

   @Mock
   private BranchRepository branchRepository;
   @Mock
   private FilterRepository filterRepository;

   @BeforeEach
   void setUp() {
      this.underTest = new BranchService(this.branchRepository, this.filterRepository);
   }

   @Test
   void testCreateFilterBranchWhenCreatingFromAnotherBranch() {
      final UUID filterId = UUID.randomUUID();
      final CreateFilterBranch createFilterBranch = new CreateFilterBranch();
      createFilterBranch.setFilterId(filterId);

      Mockito.when(this.branchRepository.findByFilter_Uuid(filterId)).thenReturn(Optional.of(new BranchEntity()));

      Assertions.assertThrows(BadRequestException.class, () -> {
         this.underTest.createFilterBranch(createFilterBranch);
      });
   }

   @Test
   void testCreateFilterBranchWhenFilterDontExists() {
      final UUID filterId = UUID.randomUUID();
      final CreateFilterBranch createFilterBranch = new CreateFilterBranch();
      createFilterBranch.setFilterId(filterId);

      Mockito.when(this.branchRepository.findByFilter_Uuid(filterId)).thenReturn(Optional.empty());
      Mockito.when(this.filterRepository.findAllByUuidOrderByVersionDesc(filterId)).thenReturn(List.of());

      Assertions.assertThrows(EntityNotFoundException.class, () -> {
         this.underTest.createFilterBranch(createFilterBranch);
      });
   }

   @Test
   void testCreateFilterBranchWhenRunsSuccessfully() {
      final UUID filterId = UUID.randomUUID();
      final CreateFilterBranch createFilterBranch = new CreateFilterBranch();
      createFilterBranch.setFilterId(filterId);

      Mockito.when(this.branchRepository.findByFilter_Uuid(filterId)).thenReturn(Optional.empty());

      final FilterEntity filterEntity = new FilterEntity();
      final UserEntity userEntity = new UserEntity();
      userEntity.setUuid(UUID.randomUUID());
      filterEntity.setUser(userEntity);
      Mockito.when(this.filterRepository.findAllByUuidOrderByVersionDesc(filterId)).thenReturn(List.of(filterEntity));

      Mockito.when(this.filterRepository.save(Mockito.any())).thenReturn(filterEntity);

      final BranchEntity branchEntity = new BranchEntity();
      branchEntity.setFilter(filterEntity);
      branchEntity.setOriginalFilter(filterEntity);

      Mockito.when(this.branchRepository.save(Mockito.any())).thenReturn(branchEntity);

      final FilterBranch filterBranch = this.underTest.createFilterBranch(createFilterBranch);

      Assertions.assertNotNull(filterBranch);
   }

   @Test
   void testDeleteFilterBranchWhenBranchDontExists() {
      final UUID branchId = UUID.randomUUID();
      Mockito.when(this.branchRepository.findByUuid(branchId)).thenReturn(Optional.empty());

      Assertions.assertThrows(EntityNotFoundException.class, () -> {
         this.underTest.deleteFilterBranch(branchId);
      });
   }

   @Test
   void testDeleteFilterBranchWhenRunsSuccessfully() {
      final UUID branchId = UUID.randomUUID();
      final BranchEntity branchEntity = new BranchEntity();
      final FilterEntity filterEntity = new FilterEntity();
      branchEntity.setFilter(filterEntity);

      Mockito.when(this.branchRepository.findByUuid(branchId)).thenReturn(Optional.of(branchEntity));
      Mockito.doNothing().when(this.branchRepository).delete(branchEntity);
      Mockito.doNothing().when(this.filterRepository).delete(filterEntity);

      this.underTest.deleteFilterBranch(branchId);
   }

   @Test
   void testMergeBranchWhenBranchDontExists() {
      final UUID branchId = UUID.randomUUID();
      Mockito.when(this.branchRepository.findByUuid(branchId)).thenReturn(Optional.empty());

      Assertions.assertThrows(EntityNotFoundException.class, () -> {
         this.underTest.mergeBranch(branchId);
      });
   }

   @Test
   void testMergeBranchWhenRunsSuccessfully() {
      final UUID branchId = UUID.randomUUID();
      final BranchEntity branchEntity = new BranchEntity();
      final FilterEntity filterEntity = new FilterEntity();
      branchEntity.setOriginalFilter(filterEntity);
      branchEntity.setFilter(filterEntity);
      filterEntity.setVersion(1L);
      Mockito.when(this.branchRepository.findByUuid(branchId)).thenReturn(Optional.of(branchEntity));

      Mockito.doNothing().when(this.branchRepository).delete(branchEntity);
      Mockito.when(this.filterRepository.save(filterEntity)).thenReturn(null);
      Mockito.doNothing().when(this.filterRepository).delete(filterEntity);

      this.underTest.mergeBranch(branchId);
   }
}
