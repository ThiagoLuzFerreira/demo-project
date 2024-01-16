package com.thiago.demoproject.controller;

import com.thiago.demoproject.dto.PersonAddressDTO;
import com.thiago.demoproject.dto.PersonDTO;
import com.thiago.demoproject.service.PersonService;
import com.thiago.demoproject.webclient.dto.AddressDTO;
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

    //todo impl swagger
    //todo impl flyway

    @Autowired
    private PersonService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<PersonDTO>> listAll(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "sort", defaultValue = "asc") String sort){

        var sortOrder = "desc".equalsIgnoreCase(sort) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortOrder, "firstName"));
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping(value = "/findByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<PersonAddressDTO>> findByEmail(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "email") String email){

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok().body(service.findPeopleByEmail(email, pageable));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person){

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(person.getId()).toUri();
        return ResponseEntity.created(location).body(service.save(person));
    }

    @GetMapping("/{cep}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable String cep){
        return ResponseEntity.ok(service.getAddress(cep));
    }
}
