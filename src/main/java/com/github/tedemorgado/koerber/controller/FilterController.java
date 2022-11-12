package com.github.tedemorgado.koerber.controller;

import com.github.tedemorgado.koerber.controller.model.Filter;
import com.github.tedemorgado.koerber.exception.EntityNotFoundException;
import com.github.tedemorgado.koerber.service.FilterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@Tag(name = "Filters")
@RestController
@RequestMapping("/filters")
public class FilterController {

   private final FilterService filterService;

   public FilterController(final FilterService filterService) {
      this.filterService = filterService;
   }

   @Operation(
      summary = "Get a list of all the filters on the latest version",
      responses = {
         @ApiResponse(
            responseCode = "200",
            description = "The list of all filters on their latest versions."
         )
      }
   )
   @GetMapping()
   @ResponseStatus(HttpStatus.OK)
   public Page<Filter> getAllFilters() {
      return this.filterService.getAllFilters(null);
   }

   @Operation(
      summary = "Get all versions from a filter",
      parameters = {
         @Parameter(
            name = "filterId",
            in = ParameterIn.PATH,
            required = true,
            example = "4413fb3e-d8f9-41ad-ace6-18816fda1e68"
         ),
         @Parameter(
            name = "version",
            in = ParameterIn.QUERY,
            example = "1"
         )
      },
      responses = {
         @ApiResponse(
            responseCode = "200",
            description = "The list of all versions from a filter."
         ),
         @ApiResponse(
            responseCode = "404",
            description = "Specified filter not found",
            content = {
               @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                     implementation = EntityNotFoundException.class
                  )
               )
            }
         )
      }
   )
   @GetMapping("/{filterId}/versions")
   @ResponseStatus(HttpStatus.OK)
   public Set<Long> getFilterVersionsById(@PathVariable final UUID filterId) {
      return this.filterService.getFilterVersions(filterId);
   }

   @Operation(
      summary = "Get filter by id",
      parameters = {
         @Parameter(
            name = "filterId",
            in = ParameterIn.PATH,
            required = true,
            example = "4413fb3e-d8f9-41ad-ace6-18816fda1e68"
         ),
         @Parameter(
            name = "version",
            in = ParameterIn.QUERY,
            example = "1"
         )
      },
      responses = {
         @ApiResponse(
            responseCode = "200",
            description = "The list of all available filters."
         ),
         @ApiResponse(
            responseCode = "404",
            description = "Specified filter not found",
            content = {
               @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                     implementation = EntityNotFoundException.class
                  )
               )
            }
         )
      }
   )
   @GetMapping("/{filterId}")
   @ResponseStatus(HttpStatus.OK)
   public Filter getFilterById(@PathVariable final UUID filterId, @RequestParam(required = false) final Long version) {
      return this.filterService.getFilterById(filterId, version);
   }
}
