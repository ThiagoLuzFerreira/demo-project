package com.thiago.demoproject.service;

import com.thiago.demoproject.model.dto.PersonAddressDTO;
import com.thiago.demoproject.model.dto.PersonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {

    Page<PersonDTO> findAll(Pageable pageable);

    Page<PersonAddressDTO> findPeopleByEmail(String email, Pageable pageable);

    PersonDTO save(PersonDTO person);

    PersonDTO updateCep(String email, String cep);
}
