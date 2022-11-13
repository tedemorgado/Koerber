package com.github.tedemorgado.koerber.service;

import com.github.tedemorgado.koerber.controller.model.CreateFilter;
import com.github.tedemorgado.koerber.controller.model.Filter;
import com.github.tedemorgado.koerber.exception.BadRequestException;
import com.github.tedemorgado.koerber.exception.EntityNotFoundException;
import com.github.tedemorgado.koerber.persistence.model.BranchEntity;
import com.github.tedemorgado.koerber.persistence.model.FilterEntity;
import com.github.tedemorgado.koerber.persistence.model.UserEntity;
import com.github.tedemorgado.koerber.persistence.repository.BranchRepository;
import com.github.tedemorgado.koerber.persistence.repository.FilterRepository;
import com.github.tedemorgado.koerber.persistence.repository.ScreenRepository;
import com.github.tedemorgado.koerber.persistence.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class FilterServiceTest {

   @Mock
   private FilterRepository filterRepository;
   @Mock
   private UserRepository userRepository;
   @Mock
   private ScreenRepository screenRepository;
   @Mock
   private BranchRepository branchRepository;
   private FilterService underTest;

   @BeforeEach
   void setUp() {
      this.underTest = new FilterService(this.filterRepository, this.userRepository, this.screenRepository, this.branchRepository);
   }

   @Test
   void testUpdateFilterWhenFilterParamIsDifferentFromTheObject() {
      final UUID filterId = UUID.randomUUID();
      final UUID filterId2 = UUID.randomUUID();
      final Filter filter = new Filter();
      filter.setId(filterId2);

      Assertions.assertThrows(BadRequestException.class, () -> {
         this.underTest.updateFilter(filterId, filter);
      });
   }

   @Test
   void testUpdateFilterWhenFilterDontExists() {
      final UUID filterId = UUID.randomUUID();
      final Filter filter = new Filter();
      filter.setId(filterId);

      Mockito.when(this.filterRepository.findAllByUuidOrderByVersionDesc(filterId)).thenReturn(List.of());

      Assertions.assertThrows(EntityNotFoundException.class, () -> {
         this.underTest.updateFilter(filterId, filter);
      });
   }

   @Test
   void testUpdateFilterWhenRunsSuccessfully() {
      final UUID filterId = UUID.randomUUID();
      final Filter filter = new Filter();
      filter.setId(filterId);

      final FilterEntity filterEntity = new FilterEntity();
      final UserEntity userEntity = new UserEntity();
      userEntity.setUuid(UUID.randomUUID());
      filterEntity.setUser(userEntity);
      filterEntity.setVersion(1L);
      Mockito.when(this.filterRepository.findAllByUuidOrderByVersionDesc(filterId)).thenReturn(List.of(filterEntity));

      Mockito.when(this.filterRepository.save(filterEntity)).thenReturn(filterEntity);
      final Filter result = this.underTest.updateFilter(filterId, filter);

      Assertions.assertNotNull(result);
   }

   @Test
   void testCreateFilterWhenUserDontExists() {
      final CreateFilter createFilter = new CreateFilter();
      createFilter.setUserId(UUID.randomUUID());

      Mockito.when(this.userRepository.findByUuid(createFilter.getUserId())).thenReturn(Optional.empty());

      Assertions.assertThrows(EntityNotFoundException.class, () -> {
         this.underTest.createFilter(createFilter);
      });
   }

   @Test
   void testCreateFilterWhenRunsSuccessfully() {
      final CreateFilter createFilter = new CreateFilter();
      createFilter.setUserId(UUID.randomUUID());

      final UserEntity userEntity = new UserEntity();
      userEntity.setUuid(createFilter.getScreenId());

      Mockito.when(this.userRepository.findByUuid(createFilter.getUserId())).thenReturn(Optional.of(userEntity));

      final FilterEntity filterEntity = new FilterEntity();
      filterEntity.setUser(userEntity);
      filterEntity.setVersion(1L);

      Mockito.when(this.filterRepository.save(Mockito.any())).thenReturn(filterEntity);

      final Filter result = this.underTest.createFilter(createFilter);

      Assertions.assertNotNull(result);
   }

   @Test
   void testDeleteFilterWhenUsedOnBranch() {
      final UUID filterId = UUID.randomUUID();
      Mockito.when(this.branchRepository.findByFilter_Uuid(filterId)).thenReturn(Optional.of(new BranchEntity()));

      Assertions.assertThrows(BadRequestException.class, () -> {
         this.underTest.deleteFilter(filterId);
      });
   }

   @Test
   void testDeleteFilterWhenAssociatedToABranch() {
      final UUID filterId = UUID.randomUUID();
      Mockito.when(this.branchRepository.findByFilter_Uuid(filterId)).thenReturn(Optional.empty());

      final FilterEntity filterEntity = new FilterEntity();
      Mockito.when(this.filterRepository.findAllByUuidOrderByVersionDesc(filterId)).thenReturn(List.of(filterEntity));
      Mockito.when(this.branchRepository.findByOriginalFilter_Id(filterEntity.getId())).thenReturn(Optional.of(new BranchEntity()));

      Assertions.assertThrows(BadRequestException.class, () -> {
         this.underTest.deleteFilter(filterId);
      });
   }

   @Test
   void testDeleteFilterWhenRunsSuccessfully() {
      final UUID filterId = UUID.randomUUID();
      Mockito.when(this.branchRepository.findByFilter_Uuid(filterId)).thenReturn(Optional.empty());


      final FilterEntity filterEntity = new FilterEntity();
      Mockito.when(this.filterRepository.findAllByUuidOrderByVersionDesc(filterId)).thenReturn(List.of(filterEntity));
      Mockito.when(this.branchRepository.findByOriginalFilter_Id(filterEntity.getId())).thenReturn(Optional.empty());

      Mockito.doNothing().when(this.filterRepository).delete(filterEntity);

      this.underTest.deleteFilter(filterId);
   }

   @Test
   void testGetFilterVersionsWhenNoFiltersFound() {
      final UUID filterId = UUID.randomUUID();
      Mockito.when(this.filterRepository.findAllByUuidOrderByVersionDesc(filterId)).thenReturn(List.of());

      Assertions.assertThrows(EntityNotFoundException.class, () -> {
         this.underTest.getFilterVersions(filterId);
      });
   }

   @Test
   void testGetFilterVersionsWhenRunsSuccessfully() {
      final UUID filterId = UUID.randomUUID();
      final FilterEntity filterEntity = new FilterEntity();
      filterEntity.setVersion(1L);
      final FilterEntity filterEntity2 = new FilterEntity();
      filterEntity2.setVersion(2L);
      Mockito.when(this.filterRepository.findAllByUuidOrderByVersionDesc(filterId)).thenReturn(List.of(filterEntity2, filterEntity));

      final Set<Long> filterVersions = this.underTest.getFilterVersions(filterId);

      Assertions.assertNotNull(filterVersions);
      Assertions.assertFalse(filterVersions.isEmpty());
      Assertions.assertEquals(2, filterVersions.size());
   }

   @Test
   void testGetFilterByIdWhenFilterDontExists() {
      final UUID filterId = UUID.randomUUID();
      Mockito.when(this.filterRepository.findAllByUuidOrderByVersionDesc(filterId)).thenReturn(List.of());

      Assertions.assertThrows(EntityNotFoundException.class, () -> {
         this.underTest.getFilterById(filterId, null);
      });
   }

   @Test
   void testGetFilterByIdWhenVersionNotSpecified() {
      final UUID filterId = UUID.randomUUID();
      final FilterEntity filterEntity1 = new FilterEntity();
      filterEntity1.setVersion(1L);
      final FilterEntity filterEntity2 = new FilterEntity();
      filterEntity2.setVersion(2L);
      final UserEntity userEntity = new UserEntity();
      filterEntity1.setUser(userEntity);
      filterEntity2.setUser(userEntity);

      Mockito.when(this.filterRepository.findAllByUuidOrderByVersionDesc(filterId)).thenReturn(List.of(filterEntity2, filterEntity1));

      final Filter result = this.underTest.getFilterById(filterId, null);

      Assertions.assertNotNull(result);
      Assertions.assertEquals(2L, result.getVersion());
   }

   @Test
   void testGetFilterByIdWhenVersionIsSpecified() {
      final UUID filterId = UUID.randomUUID();
      final FilterEntity filterEntity1 = new FilterEntity();
      filterEntity1.setVersion(1L);
      final FilterEntity filterEntity2 = new FilterEntity();
      filterEntity2.setVersion(2L);
      final UserEntity userEntity = new UserEntity();
      filterEntity1.setUser(userEntity);
      filterEntity2.setUser(userEntity);

      Mockito.when(this.filterRepository.findAllByUuidOrderByVersionDesc(filterId)).thenReturn(List.of(filterEntity2, filterEntity1));

      final Filter result = this.underTest.getFilterById(filterId, 1L);

      Assertions.assertNotNull(result);
      Assertions.assertEquals(1L, result.getVersion());
   }
}
