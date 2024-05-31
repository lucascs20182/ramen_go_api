package br.com.redventures.ramen_go.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class MissingApiKeyException extends RuntimeException {

  public MissingApiKeyException() {
    super("x-api-key header missing");
  }

}
