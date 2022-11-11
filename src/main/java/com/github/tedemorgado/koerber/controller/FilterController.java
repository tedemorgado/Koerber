package com.github.tedemorgado.koerber.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Filters")
@RestController
@RequestMapping("/filters")
public class FilterController {

   @Operation(
      summary = "Get list of filter",
      parameters = {},
      responses = {
         @ApiResponse(
            responseCode = "200",
            description = "The list of all available filters."
         )
      }
   )

   @GetMapping
   @ResponseStatus(HttpStatus.OK)
   public String getAllFilters() {
      return "service.getAllMarkets()";
   }
}
