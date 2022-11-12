package com.github.tedemorgado.koerber.controller.model;

import java.util.UUID;

public class Filter extends CreateFilter {

   private final UUID id;
   private final Long version;

   public Filter(final UUID id, final UUID userId, final String name, final String data, final String outputFilter,
                 final UUID screenId, final Long version) {
      super(userId, name, data, outputFilter, screenId);
      this.id = id;
      this.version = version;
   }

   public UUID getId() {
      return this.id;
   }

   public Long getVersion() {
      return this.version;
   }
}
