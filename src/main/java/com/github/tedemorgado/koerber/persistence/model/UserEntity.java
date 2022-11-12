package com.github.tedemorgado.koerber.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "users")
//@Audited
public class UserEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private UUID uuid;

   private String name;

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

   public String getName() {
      return this.name;
   }

   public void setName(final String name) {
      this.name = name;
   }
}
