package com.thiago.demoproject.service;

import com.thiago.demoproject.dto.PersonDTO;
import com.thiago.demoproject.mapper.GenericModelMapper;
import com.thiago.demoproject.model.Person;
import com.thiago.demoproject.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class PersonService {

    Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository repository;

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

        //TODO impl ContollerAdvice and exeption for already registred email

        logger.info("Saving a person");
        Person savedPerson = repository.save(GenericModelMapper.parseObject(person, Person.class));
        return GenericModelMapper.parseObject(savedPerson, PersonDTO.class);
    }
}
