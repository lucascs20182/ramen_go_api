package br.com.redventures.ramen_go.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidApiKeyException extends RuntimeException {

  public InvalidApiKeyException() {
    super("x-api-key header has an invalid API key");
  }

}
