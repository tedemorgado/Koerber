package com.github.tedemorgado.koerber.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InternalExceptionHandler {

   @ExceptionHandler(value = {EntityNotFoundException.class})
   public ResponseEntity<InternalException> handleEntityNotFound(final EntityNotFoundException e) {
      final InternalException internalException = new InternalException(e.getMessage(), e.getCause());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(internalException);
   }

   @ExceptionHandler(value = {BadRequestException.class})
   public ResponseEntity<InternalException> handleBadRequest(final BadRequestException e) {
      final InternalException internalException = new InternalException(e.getMessage(), e.getCause());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(internalException);
   }
}
