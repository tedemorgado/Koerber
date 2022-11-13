package com.github.tedemorgado.koerber.controller.model;

import java.util.UUID;

public class FilterBranch {

   private UUID id;

   private Filter filter;

   public UUID getId() {
      return this.id;
   }

   public void setId(final UUID id) {
      this.id = id;
   }

   public Filter getFilter() {
      return this.filter;
   }

   public void setFilter(final Filter filter) {
      this.filter = filter;
   }
}

