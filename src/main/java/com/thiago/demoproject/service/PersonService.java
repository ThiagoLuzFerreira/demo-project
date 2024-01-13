package com.thiago.demoproject.service;

import com.thiago.demoproject.dto.PersonAddressDTO;
import com.thiago.demoproject.dto.PersonDTO;
import com.thiago.demoproject.exception.DataIntegrityViolationException;
import com.thiago.demoproject.mapper.GenericModelMapper;
import com.thiago.demoproject.model.Person;
import com.thiago.demoproject.repository.PersonRepository;
import com.thiago.demoproject.webclient.AddressFeingClient;
import com.thiago.demoproject.webclient.dto.AddressDTO;
import com.thiago.demoproject.webclient.model.Address;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PersonService {

    Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository repository;

    @Autowired
    private AddressFeingClient addressFeingClient;

    public Page<PersonDTO> findAll(Pageable pageable){

        logger.info("Finding all people");
        Page<Person> peoplePage = repository.findAll(pageable);
        return peoplePage.map(p -> GenericModelMapper.parseObject(p, PersonDTO.class));
    }

    public Page<PersonDTO> findPeopleByEmail(String email, Pageable pageable) {

        logger.info("Finding people by email");
        Page<Person> peopleByEmail = repository.findPeopleByEmail(email, pageable);
        return peopleByEmail.map(p -> GenericModelMapper.parseObject(p, PersonDTO.class));
    }

    @Transactional
    public PersonDTO save(PersonDTO person) {

        logger.info("Saving a person");
        Optional<Person> foundPerson = repository.findByEmail(person.getEmail());
        if(foundPerson.isPresent() && foundPerson.get().getEmail().equals(person.getEmail())){
            throw new DataIntegrityViolationException("Already registred email");
        }
        Person savedPerson = repository.save(GenericModelMapper.parseObject(person, Person.class));
        return GenericModelMapper.parseObject(savedPerson, PersonDTO.class);
    }

    public AddressDTO getAddress(String cep){
        Address address = addressFeingClient.getAddress(cep).getBody();
        return GenericModelMapper.parseObject(address, AddressDTO.class);
    }
}
