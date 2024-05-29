package br.com.redventures.ramen_go.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.redventures.ramen_go.entities.BrothEntity;
import br.com.redventures.ramen_go.services.BrothService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/broths")
@Tag(name = "default")
public class BrothController {

  private final String correctApiKey = "ZtVdh8XQ2U8pWI2gmZ7f796Vh8GllXoN7mr0djNf";

  @Autowired
  private BrothService service;

  @GetMapping("/")
  @Operation(summary = "List all available broths")
  @ApiResponse(
    responseCode = "200",
    description = "A list of broths",
    content = @Content(
      mediaType="application/json",
      schema=@Schema(implementation=BrothEntity.class)
    )
  )
  public ResponseEntity<?> listAll(@RequestHeader("x-api-key") String apiKey) {

    if (apiKey == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("API key is missing");
    }

    if (!apiKey.equals(correctApiKey)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key");
    }

    List<BrothEntity> entities = service.listAll();
    return ResponseEntity.status(HttpStatus.OK).body(entities);
  }

}
