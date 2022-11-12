package com.github.tedemorgado.koerber.exception;

public class InternalException {

   private String message;
   private Throwable cause;

   public InternalException(final String message, final Throwable cause) {
      this.message = message;
      this.cause = cause;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(final String message) {
      this.message = message;
   }

   public Throwable getCause() {
      return this.cause;
   }

   public void setCause(final Throwable cause) {
      this.cause = cause;
   }
}
