package com.thiago.demoproject.controller;

import com.thiago.demoproject.dto.PersonDTO;
import com.thiago.demoproject.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping
    ResponseEntity<Page<PersonDTO>> listAll(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping(value = "/findByEmail")
    ResponseEntity<Page<PersonDTO>> findByEmail(
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "email") String email){

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok().body(service.findPeopleByEmail(email, pageable));
    }

    @PostMapping
    ResponseEntity<PersonDTO> create(@RequestBody PersonDTO person){

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(person.getId()).toUri();
        return ResponseEntity.created(location).body(service.save(person));
    }
}
