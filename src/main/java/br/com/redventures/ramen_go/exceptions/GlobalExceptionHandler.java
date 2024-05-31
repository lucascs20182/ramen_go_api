package br.com.redventures.ramen_go.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MissingApiKeyException.class)
  public ResponseEntity<?> handleMissingApiKeyException(MissingApiKeyException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
      .body(new ErrorResponse(ex.getMessage()));
  }

  @ExceptionHandler(InvalidApiKeyException.class)
  public ResponseEntity<?> handleInvalidApiKeyException(InvalidApiKeyException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
      .body(new ErrorResponse(ex.getMessage()));
  }

  @ExceptionHandler(InvalidRequestException.class)
  public ResponseEntity<?> handleInvalidRequestException(InvalidRequestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(new ErrorResponse(ex.getMessage()));
  }

  @ExceptionHandler(InternalServerErrorException.class)
  public ResponseEntity<?> handleInternalServerErrorException(InternalServerErrorException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(new ErrorResponse(ex.getMessage()));
  }

  static class ErrorResponse {
    private final String error;

    public ErrorResponse(String error) {
      this.error = error;
    }

    public String getError() {
      return error;
    }
  }

}
