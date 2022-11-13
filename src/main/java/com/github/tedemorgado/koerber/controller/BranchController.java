package com.github.tedemorgado.koerber.controller;

import com.github.tedemorgado.koerber.controller.model.CreateFilterBranch;
import com.github.tedemorgado.koerber.controller.model.FilterBranch;
import com.github.tedemorgado.koerber.exception.InternalException;
import com.github.tedemorgado.koerber.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Branches")
@RestController
@RequestMapping("/branches")
public class BranchController {

   private final BranchService branchService;

   public BranchController(final BranchService branchService) {
      this.branchService = branchService;
   }

   @Operation(
      summary = "Create new branch",
      responses = {
         @ApiResponse(
            responseCode = "201",
            description = "New branch created"
         ),
         @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = {
               @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                     implementation = InternalException.class
                  )
               )
            }
         ),
         @ApiResponse(
            responseCode = "404",
            description = "Filter not found",
            content = {
               @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(
                     implementation = InternalException.class
                  )
               )
            }
         )
      }
   )
   @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
   @ResponseStatus(HttpStatus.CREATED)
   public FilterBranch createFilterBranch(@RequestBody final CreateFilterBranch createFilterBranch) {
      return this.branchService.createFilterBranch(createFilterBranch);
   }
}
