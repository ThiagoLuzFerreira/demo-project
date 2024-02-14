package com.thiago.demoproject.controller;

import com.thiago.demoproject.model.dto.PersonAddressDTO;
import com.thiago.demoproject.model.dto.PersonDTO;
import com.thiago.demoproject.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    //todo impl security, jwt token, oauth2, refresh token on redis
    //todo set a homologation profile to dockerize it all
    //todo dockerize everything into a single docker compose and create a project with modules. remove config server and pass enviroment variables to SMTP via docker compose file

    @Autowired
    private PersonService service;

    @Operation(summary = "Find people",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<PersonDTO>> listAll(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "sort", defaultValue = "asc") String sort){

        var sortOrder = "desc".equalsIgnoreCase(sort) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortOrder, "firstName"));
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @Operation(summary = "Find people by email",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = PersonAddressDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    @GetMapping(value = "/findByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<PersonAddressDTO>> findByEmail(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "email") String email){

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok().body(service.findPeopleByEmail(email, pageable));
    }

    @Operation(summary = "Adds a new person",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PersonDTO> create(@RequestBody @Valid PersonDTO person){

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/findByEmail").queryParam("email", person.getEmail()).buildAndExpand().toUri();
        return ResponseEntity.created(location).body(service.save(person));
    }

    @Operation(summary = "Updates a person cep", description = "Updates a person cep",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
            }
    )
    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PersonDTO> updateCep(@RequestParam String email,
                                        @RequestParam String cep){

        var person = service.updateCep(email, cep);
        return ResponseEntity.ok(person);
    }
}
