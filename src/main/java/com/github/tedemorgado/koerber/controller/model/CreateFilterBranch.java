package com.github.tedemorgado.koerber.controller.model;

import java.util.UUID;

public class CreateFilterBranch {

   private UUID filterId;

   public UUID getFilterId() {
      return this.filterId;
   }

   public void setFilterId(final UUID filterId) {
      this.filterId = filterId;
   }
}
