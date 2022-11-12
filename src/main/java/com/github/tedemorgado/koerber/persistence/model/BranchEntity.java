package com.github.tedemorgado.koerber.persistence.model;

import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "branch")
@Audited
public class BranchEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private UUID uuid;

   @ManyToOne(
      cascade = {CascadeType.ALL},
      fetch = FetchType.LAZY,
      optional = false
   )
   @JoinColumn(name = "original_filter_id")
   private FilterEntity originalFilter;

   private Long originalFilterVersion;

   @ManyToOne(
      cascade = {CascadeType.ALL},
      fetch = FetchType.LAZY,
      optional = false
   )
   @JoinColumn(name = "filter_id")
   private FilterEntity filter;

   private Long version;

   public Long getId() {
      return this.id;
   }

   public void setId(final Long id) {
      this.id = id;
   }

   public UUID getUuid() {
      return this.uuid;
   }

   public void setUuid(final UUID uuid) {
      this.uuid = uuid;
   }

   public FilterEntity getOriginalFilter() {
      return this.originalFilter;
   }

   public void setOriginalFilter(final FilterEntity originalFilter) {
      this.originalFilter = originalFilter;
   }

   public Long getOriginalFilterVersion() {
      return this.originalFilterVersion;
   }

   public void setOriginalFilterVersion(final Long originalFilterVersion) {
      this.originalFilterVersion = originalFilterVersion;
   }

   public FilterEntity getFilter() {
      return this.filter;
   }

   public void setFilter(final FilterEntity filter) {
      this.filter = filter;
   }

   public Long getVersion() {
      return this.version;
   }

   public void setVersion(final Long version) {
      this.version = version;
   }
}
