package com.thiago.demoproject.service;

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

    public Page<Person> findAll(Pageable pageable){

        logger.info("Finding all people");
        return repository.findAll(pageable);
    }

    public Page<Person> findPeopleByEmail(String email, Pageable pageable) {
        logger.info("Finding people by email");
        return repository.findPeopleByEmail(email, pageable);
    }

    @Transactional
    public Person save(Person person) {

        //TODO impl ContollerAdvice and exeption for already registred email

        logger.info("Saving a person");
        return repository.save(person);
    }
}
