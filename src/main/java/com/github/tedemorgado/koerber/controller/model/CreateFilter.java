package com.github.tedemorgado.koerber.controller.model;

import java.util.UUID;

public class CreateFilter {

   private UUID userId;
   private String name;
   private String data;
   private String outputFilter;
   private UUID screenId;

   public CreateFilter() {
   }

   public CreateFilter(final UUID userId, final String name, final String data, final String outputFilter, final UUID screenId) {
      this.userId = userId;
      this.name = name;
      this.data = data;
      this.outputFilter = outputFilter;
      this.screenId = screenId;
   }

   public UUID getUserId() {
      return this.userId;
   }

   public void setUserId(final UUID userId) {
      this.userId = userId;
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

   public UUID getScreenId() {
      return this.screenId;
   }

   public void setScreenId(final UUID screenId) {
      this.screenId = screenId;
   }
}
