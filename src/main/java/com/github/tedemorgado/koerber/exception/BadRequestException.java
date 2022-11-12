package com.github.tedemorgado.koerber.exception;

public class BadRequestException extends RuntimeException {

   public BadRequestException(final String message) {
      super(message);
   }
}
