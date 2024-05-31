package br.com.redventures.ramen_go.configs;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PlainTextToJsonMessageConverter implements HttpMessageConverter<Object> {

  private final ObjectMapper objectMapper;

    public PlainTextToJsonMessageConverter(ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
      return Collections.singletonList(MediaType.TEXT_PLAIN);
    }

    @Override
    public Object read(Class<? extends Object> clazz, HttpInputMessage inputMessage)
        throws IOException, HttpMessageNotReadableException {
      InputStreamReader reader = new InputStreamReader(inputMessage.getBody(), StandardCharsets.UTF_8);
      try {
          return objectMapper.readValue(reader, clazz);
      } catch (Exception e) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid JSON input", e);
      }
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
      return mediaType != null && mediaType.equalsTypeAndSubtype(MediaType.TEXT_PLAIN);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
      return false; // We only read plain text, not write it
    }

    @Override
    public void write(Object t, MediaType contentType, HttpOutputMessage outputMessage)
        throws IOException, HttpMessageNotWritableException {
      // Not needed
      throw new UnsupportedOperationException("Unimplemented method 'write'");
    }

}
