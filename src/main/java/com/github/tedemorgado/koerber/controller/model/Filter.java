package com.github.tedemorgado.koerber.controller.model;

import java.util.UUID;

public class Filter {

   private final UUID id;
   private final UUID userId;
   private final String name;
   private final String data;
   private final String outputFilter;
   private final UUID screenId;
   private final Long version;

   public Filter(final UUID id, final UUID userId, final String name, final String data, final String outputFilter,
                 final UUID screenId, final Long version) {
      this.id = id;
      this.userId = userId;
      this.name = name;
      this.data = data;
      this.outputFilter = outputFilter;
      this.screenId = screenId;
      this.version = version;
   }

   public UUID getId() {
      return this.id;
   }

   public UUID getUserId() {
      return this.userId;
   }

   public String getName() {
      return this.name;
   }

   public String getData() {
      return this.data;
   }

   public String getOutputFilter() {
      return this.outputFilter;
   }

   public UUID getScreenId() {
      return this.screenId;
   }

   public Long getVersion() {
      return this.version;
   }
}
