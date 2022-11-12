package com.github.tedemorgado.koerber.persistence.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "filter")
//@Audited
public class FilterEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private UUID uuid;

   @ManyToOne(
      cascade = {CascadeType.ALL},
      fetch = FetchType.EAGER,
      optional = false
   )
   @JoinColumn(name = "user_id")
   private UserEntity user;

   private String name;

   @Column(name = "Data")
   @Lob
   private String data;

   @Column(name = "output_filter")
   private String outputFilter;

   @ManyToOne(
      cascade = {CascadeType.ALL},
      fetch = FetchType.EAGER
   )
   @JoinColumn(name = "screen_id")
   private ScreenEntity screen;

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

   public UserEntity getUser() {
      return this.user;
   }

   public void setUser(final UserEntity user) {
      this.user = user;
   }

   public String getName() {
      return this.name;
   }

   public void setName(final String name) {
      this.name = name;
   }

   public String getData() {
      return this.data;
   }

   public void setData(final String data) {
      this.data = data;
   }

   public String getOutputFilter() {
      return this.outputFilter;
   }

   public void setOutputFilter(final String outputFilter) {
      this.outputFilter = outputFilter;
   }

   public ScreenEntity getScreen() {
      return this.screen;
   }

   public void setScreen(final ScreenEntity screen) {
      this.screen = screen;
   }

   public Long getVersion() {
      return this.version;
   }

   public void setVersion(final Long version) {
      this.version = version;
   }
}
