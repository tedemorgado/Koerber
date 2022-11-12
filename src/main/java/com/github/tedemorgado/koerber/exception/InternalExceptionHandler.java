package com.github.tedemorgado.koerber.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InternalExceptionHandler {

   @ExceptionHandler(value = {EntityNotFoundException.class})
   public ResponseEntity<RuntimeException> handleEntityNotFound(final EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
   }
}
